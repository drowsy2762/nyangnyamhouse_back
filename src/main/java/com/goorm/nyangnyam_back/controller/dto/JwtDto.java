package com.goorm.nyangnyam_back.controller.dto;

public record JwtDto(
        String accessToken,
        String refreshToken,
        String grantType
) {
}
