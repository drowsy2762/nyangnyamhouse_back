package com.goorm.nyangnyam_back.controller;

import com.goorm.nyangnyam_back.service.ReissueService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * ReissueController 클래스
 *
 * 코드 작성자:
 *      -서진영(jin2304)
 *
 * 코드 설명:
 *      -ReissueController는 refresh 토큰을 통해 새로운 access 토큰과 refresh 토큰 재발급을 처리하는 컨트롤러.
 *      -refresh 토큰을 가져와 유효성 검사.
 *      -refresh 토큰이 유효하지 않은 경우, 클라이언트에 401 응답 코드를 전송함.
 *      -refresh 토큰이 유효한 경우, 새로운 access 토큰과 refresh 토큰을 생성하여 헤더에 재발급.
 *
 * 코드 주요 기능:
 *      -/reissue 엔드포인트를 처리하며, refresh 토큰을 검증하고, 새로운 access 토큰과 refresh 토큰을 재발급.
 *
 * 코드 작성일:
 *      -2024.08.19 ~ 2024.08.19
 */
@Controller
@ResponseBody
public class ReissueController {

    public final ReissueService reissueService;

    @Autowired
    public ReissueController(ReissueService reissueService) {
        this.reissueService = reissueService;
    }


    /**
     *   Refresh 토큰으로 새로운 Access 토큰과 Refresh 토큰을 재발급하는 메서드
     */
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {

        // 요청 헤더에서 refresh 토큰 가져오기
        String refresh = request.getHeader("refresh");
        
        // refresh 토큰 유효성 검사 및 새로운 access 토큰 및 refresh 토큰 재발급
        try {
            String[] tokens = reissueService.reissueTokens(refresh);
            String newAccess = tokens[0];
            String newRefresh = tokens[1];

            // 응답헤더에 새로 발급된 access 토큰과 refresh 토큰 재발급
            response.setHeader("access", newAccess);
            response.setHeader("refresh", newRefresh);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
