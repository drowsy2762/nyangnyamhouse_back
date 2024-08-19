package com.goorm.nyangnyam_back.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.goorm.nyangnyam_back.document.Refresh;
import com.goorm.nyangnyam_back.document.User;
import com.goorm.nyangnyam_back.dto.Response.KakaoResponse;
import com.goorm.nyangnyam_back.dto.Response.LoginResponse;
import com.goorm.nyangnyam_back.repository.RefreshRepository;
import com.goorm.nyangnyam_back.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * LoginFilter 클래스
 *
 * 코드 작성자:
 *      -서진영(jin2304)
 *
 * 코드 설명:
 *      -LoginFilter는 사용자가 소셜 로그인 요청 시, 회원가입 및 로그인(사용자 인증)을 처리하는 커스텀 필터, UsernamePasswordAuthenticationFilter를 상속받음.
 *      -attemptAuthentication() 메서드에서 사용자의 정보를 추출하여 소셜 서비스에 맞게 토큰으로 만든 후, 스프링 시큐리티의 AuthenticationManager를 통해 인증을 시도.
 *      -AuthenticationManager는 CustomAuthenticationProvider를 통해 인증을 수행
 *      -로그인 성공(인증 성공)시 2개의 JWT(access, refresh 토큰) 발급, 로그인 실패 시 에러 코드 반환.
 *
 * 코드 주요 기능:
 *      -attemptAuthentication(): 사용자가 소셜 로그인 시 호출되며, 회원가입 및 로그인을 처리함. AuthenticationManager를 통해 사용자 인증을 시도.
 *      -successfulAuthentication(): 로그인 성공 시 2개의 JWT를 생성하여 응답 헤더에 추가.
 *      -unsuccessfulAuthentication(): 로그인 실패 시 401 응답 코드를 반환.
 *
 *
 * 토큰의 종류
 *  1) Access Token:
 *      -짧은 유효기간(10분), 주로 인증에 사용 (헤더에 발급 후 프론트에서 로컬 스토리지 저장)
 *      -사용자가 특정 리소스나 API에 접근할 수 있도록 권한을 부여하는 단기 유효 토큰
 *
 *  2) Refresh Token:
 *      -긴 유효기간(24시간), Access Token 갱신에 사용 (헤더에 발급)
 *      -Access 토큰이 만료되었을 때, 새로운 Access 토큰을 발급받기 위해 사용되는 장기 유효 토큰
 *
 *
 * 코드 작성일:
 *      -2024.08.18 ~ 2024.08.19
 */
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    public LoginFilter(AuthenticationManager authenticationManager, UserRepository userRepository, JWTUtil jwtUtil, RefreshRepository refreshRepository) {
        this.setFilterProcessesUrl("/auth/login");  // 공통 로그인 엔드포인트 설정
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
    }



    /**
     *  소셜 회원가입 및 로그인 인증 처리
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException  {
        System.out.println("로그인 시도");

        try {
            // 1. 요청 본문에서 JSON 데이터를 읽어오기
            ServletInputStream inputStream = request.getInputStream();
            String jsonString = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

            // 2. JSON 데이터를 Map으로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> jsonMap = objectMapper.readValue(jsonString, Map.class);

            // 3. 소셜 로그인 응답 데이터를 처리
            String provider = (String) jsonMap.get("provider");
            LoginResponse loginResponse = null;

            if (provider.equals("kakao")) {
                loginResponse = new KakaoResponse(jsonMap);
            } else if (provider.equals("naver")) {
                // 네이버 로직 처리
                // loginResponse = new NaverResponse(jsonMap);
            } else if (provider.equals("google")) {
                // 구글 로직 처리
                // loginResponse = new GoogleResponse(jsonMap);
            } else {
                throw new IllegalArgumentException("Unsupported social login provider: " + provider);
            }

            String providerId = loginResponse.getProviderId();
            String email = loginResponse.getEmail();
            String name = loginResponse.getName();
            String username = provider + providerId; //제공자 + 제공자 발급 아이디로 새로운 아이디 설정


            System.out.println("username:" + username + " provider:" +  provider + " providerId:" + providerId + " name:" + name + " email:" + email);



            if (provider == null || providerId == null || name == null || email == null) {
                throw new IllegalArgumentException("Required social login parameter not found: provider, socialId, name or email.");
            }


            // 사용자가 회원가입 했는지 DB 조회
            User user = userRepository.findByUsername(username);

            // 사용자가 없으면 회원가입 처리
            if (user == null) {
                user = new User();
                user.setUsername(username);
                user.setProvider(provider);
                user.setProviderId(providerId);
                user.setEmail(email);
                user.setName(name);
                user.setRole("ROLE_ADMIN");
                userRepository.save(user);
            }
            

            // Spring Security 사용자의 권한을 검증하기 위해, 사용자 권한 설정
            List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));

            // 사용자 정보로 UsernamePasswordAuthenticationToken 생성
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.getUsername(), null, authorities);

            // AuthenticationManager에 인증 토큰을 전달하여 인증을 수행
            // AuthenticationManager는 CustomAuthenticationProvider를 통해 실제 인증 로직을 처리
            return authenticationManager.authenticate(authToken);

        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON data", e);
        }
    }


    /**
     * 로그인 성공 시 실행하는 메소드(여기서 2개의 JWT 발급)
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {

        // 사용자 정보 가져오기
        String username = authentication.getName();


        // 사용자의 권한 정보를 가져옴
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities(); //사용자의 권한 정보를 Collection 형태로 가져옴
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator(); //권한 정보를 순회하기 위한 Iterator 생성
        GrantedAuthority auth = iterator.next();  //Iterator에서 첫 번째 권한 객체를 가져옴
        String role = auth.getAuthority();


        //2개의 JWT(access, refresh) 토큰 생성
        String access = jwtUtil.createJwt("access", username, role, 10*60*1000L);      //유효 시간: 10분(10 * 60 * 1초)
        String refresh = jwtUtil.createJwt("refresh", username, role, 24*60*60*1000L); //유효 시간: 24시간(24 * 60 * 60 * 1초)


        //Refresh 토큰 저장
        saveRefreshDocument(username, refresh, 86400000L);

        //응답 헤더에 2개의 JWT 발급
        response.setHeader("access", access);
        response.setHeader("refresh", refresh);
        response.setStatus(HttpStatus.OK.value());
    }


    /**
     * 로그인 실패 시 실행하는 메소드
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        System.out.println("로그인 실패: " + failed.getMessage());
        response.getWriter().write("Authentication failed: " + failed.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }


    /**
     *  DB에 refresh 토큰 저장 메서드
     */
    private void saveRefreshDocument(String username, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);
        Refresh refreshDocument = new Refresh();
        refreshDocument.setUsername(username);
        refreshDocument.setRefresh(refresh);
        refreshDocument.setExpiration(date.toString());

        refreshRepository.save(refreshDocument);
    }
}