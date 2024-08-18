package com.goorm.nyangnyam_back.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.goorm.nyangnyam_back.document.User;
import com.goorm.nyangnyam_back.dto.Response.KakaoResponse;
import com.goorm.nyangnyam_back.dto.Response.LoginResponse;
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
 *
 * 코드 주요 기능:
 *      -attemptAuthentication(): 사용자가 소셜 로그인 시 호출되며, 회원가입 및 로그인을 처리함. AuthenticationManager를 통해 사용자 인증을 시도.
 *

 * 코드 작성일:
 *      -2024.08.18 ~ 2024.08.18
 */
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public LoginFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.setFilterProcessesUrl("/auth/login");  // 공통 로그인 엔드포인트 설정
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
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
                user.setRole("ROLE_USER");
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
     * 로그인 성공 시 실행하는 메소드
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        System.out.println("로그인 성공");
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
}