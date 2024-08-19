package com.goorm.nyangnyam_back.jwt;

import com.goorm.nyangnyam_back.document.User;
import com.goorm.nyangnyam_back.dto.CustomUserDetails;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;


/**
 * JWTFilter 클래스
 *
 * 코드 작성자:
 *      -서진영(jin2304)
 *
 * 코드 설명:
 *      -JWTFilter 클래스는 JWT 기반 인증을 처리하는 커스텀 필터, OncePerRequestFilter를 상속받음.
 *      -doFilterInternal() 메서드는 들어오는 모든 HTTP 요청에 대해 호출되며,
 *      요청 헤더에서 JWT를 추출한 후, JWT 토큰의 유효성을 검사하고 이에 따라 사용자 인증을 처리함.
 *
 *
 * 코드 주요 기능:
 *      -doFilterInternal():
 *        사용자 인증 처리
 *         -토큰이 없을 경우, 필터는 요청을 다음 필터로 넘기며 권한이 필요한 페이지에 대해 Spring Security가 인증 실패로 처리
 *         -토큰이 유효하지 않은 경우, 즉 토큰이 만료된 경우 클라이언트에 401 응답 코드를 전송함.
 *         -토큰이 유효한 경우, 해당 토큰에서 사용자 정보를 추출하여 Spring Security의 인증 정보를 SecurityContext에 저장.
 *
 * 코드 작성일:
 *      -2024.08.19 ~ 2024.08.19
 */
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("JWT 필터");
        // 헤더에서 access키에 담긴 토큰을 꺼냄
        String accessToken = request.getHeader("access");


        //토큰이 존재하는지 검증
        if (accessToken == null) {
            //토큰이 없다면 다음 필터로 넘김
            filterChain.doFilter(request, response);
            return;
        }


        // 토큰 만료 여부 확인 (만료시 다음 필터로 넘기지 않음)
        try{
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {
            //response body
            PrintWriter writer = response.getWriter();  //응답 본문을 작성하기 위해 PrintWriter 객체를 얻음
            writer.print("access token expired");       //응답 본문에 문자열 작성
            //response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }



        // 토큰이 access인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(accessToken);
        if (!category.equals("access")) {
            //response body
            PrintWriter writer = response.getWriter();
            writer.print("invalid access token");

            //response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }



        //토큰이 유효한 경우
        //토큰에서 username과 role 획득
        String username = jwtUtil.getUsername(accessToken);
        String role = jwtUtil.getRole(accessToken);

        User user = new User();
        user.setUsername(username);
        user.setRole(role);

        //UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        //요청이 들어올 때 잠시 동안 세션에 저장
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
