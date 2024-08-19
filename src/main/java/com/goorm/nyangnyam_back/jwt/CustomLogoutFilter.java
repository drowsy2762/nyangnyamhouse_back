package com.goorm.nyangnyam_back.jwt;

import com.goorm.nyangnyam_back.repository.RefreshRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;


/**
 * CustomLogoutFilter 클래스
 *
 * 코드 작성자:
 *   서진영(jin2304)
 *
 * 코드 설명:
 *      -CustomLogoutFilter를 통해 로그아웃 로직 구현:
 *        Refresh 토큰을 받아서 토큰의 유효성 검사 후, 유효하다면 DB에서 해당 Refresh 토큰 삭제 및 응답 헤더에서 Refresh 토큰 제거
 *
 * 코드 주요 기능:
 *      (백엔드측)
 *        1) DB에 저장하고 있는 Refresh 토큰 삭제
 *        2) 응답 헤더에서 Refresh 토큰 제거
 *
 * 코드 작성일:
 *      -2024.08.19 ~ 2024.08.19
 *
 */
public class CustomLogoutFilter extends GenericFilterBean {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    public CustomLogoutFilter(JWTUtil jwtUtil, RefreshRepository refreshRepository) {
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        doFilter((HttpServletRequest)request, (HttpServletResponse)response, chain);
    }


    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        // 로그아웃 요청 URI 확인
        String requestUri = request.getRequestURI();
        if(!requestUri.matches("^\\/logout$")) {
            filterChain.doFilter(request, response);
            return;
        }


        // HTTP 메서드 확인
        String requestMethod = request.getMethod();
        if(!requestMethod.equals("POST")) {
            filterChain.doFilter(request, response);
            return;
        }


        // 헤더에서 refresh 토큰 가져오기
        String refresh = request.getHeader("refresh");


        // refresh 토큰 유효성 검사
        try {
            refresh = jwtUtil.validateToken(refresh);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage());
            return;
        }


        // 로그아웃 진행
        // 1) Refresh 토큰 DB에서 제거
        refreshRepository.deleteByRefresh(refresh);

        // 2) 응답 헤더에서 Refresh 토큰 제거 (헤더 값을 빈 문자열로 설정하여 토큰을 제거)
        response.setHeader("refresh", "");

        response.setStatus(HttpServletResponse.SC_OK);
    }

}
