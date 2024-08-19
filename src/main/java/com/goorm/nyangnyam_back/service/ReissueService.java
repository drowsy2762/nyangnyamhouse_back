package com.goorm.nyangnyam_back.service;


import com.goorm.nyangnyam_back.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ReissueService {

    private final JWTUtil jwtUtil;

    @Autowired
    public ReissueService(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    /**
     * refresh 토큰을 검증하고, 새로운 access 토큰을 반환하는 메서드
     */
    public String[] reissueTokens(String refresh) {
        // refresh 토큰 유효성 검사
        refresh = jwtUtil.validateToken(refresh);

        // 토큰이 유효한 경우
        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        // 새로운 access 토큰 생성
        String newAccess = jwtUtil.createJwt("access", username, role, 10*60*1000L);

        // 새로운 access 토큰 반환
        return new String[]{newAccess};
    }
}
