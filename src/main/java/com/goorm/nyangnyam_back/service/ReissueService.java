package com.goorm.nyangnyam_back.service;


import com.goorm.nyangnyam_back.document.Refresh;
import com.goorm.nyangnyam_back.jwt.JWTUtil;
import com.goorm.nyangnyam_back.repository.RefreshRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class ReissueService {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    @Autowired
    public ReissueService(JWTUtil jwtUtil, RefreshRepository refreshRepository) {
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
    }


    /**
     * refresh 토큰을 검증하고, 새로운 access 및 refresh 토큰을 반환하는 메서드
     */
    public String[] reissueTokens(String refresh) {
        // refresh 토큰 유효성 검사
        refresh = jwtUtil.validateToken(refresh);

        // 토큰이 유효한 경우
        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        // 새로운 access 토큰 및 refresh 토큰 생성
        String newAccess = jwtUtil.createJwt("access", username, role, 10*60*1000L);
        String newRefresh = jwtUtil.createJwt("refresh", username, role, 24*60*60*1000L);

        // 기존의 refresh 토큰 삭제 후, 새 refresh 토큰을 DB에 저장
        refreshRepository.deleteByRefresh(refresh);
        saveRefreshDocument(username, newRefresh, 24 * 60 * 60 * 1000L);

        // 새로운 access 토큰 및 refresh 토큰 반환
        return new String[]{newAccess, newRefresh};
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
