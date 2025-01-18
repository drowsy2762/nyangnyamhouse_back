package com.goorm.nyangnyam_back.dto.Response;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

public class KakaoResponse implements LoginResponse{

    private final Map<String, Object> attributes;

    public KakaoResponse(Map<String, Object> attributes) {
        this.attributes = attributes;
    }


    @Override
    public String getProvider() {
        String provider = (String) attributes.get("provider");
        if(provider == null) {
            throw new IllegalArgumentException("Kakao 응답에 제공자 정보가 포함되어 있지 않습니다.");
        }
        return provider;
    }

    @Override
    public String getProviderId() {
        return null;
    }

    @Override
    public String getEmail() {
        String email = (String) attributes.get("email");
        if(email == null) {
            throw new IllegalArgumentException("Kakao 응답에 이메일 정보가 포함되어 있지 않습니다.");
        }
        return email;
    }


    @Override
    public String getUsername() {
        // 이메일을 바이트 배열로 변환하여 UUID v5 를 생성 (UUID v5: 동일한 입력에 대해 일관된 고유 식별자를 생성)
        UUID emailUUID = UUID.nameUUIDFromBytes(attributes.get("email").toString().getBytes(StandardCharsets.UTF_8));
        String username = attributes.get("provider").toString() + emailUUID;   // '제공자 + 이메일 기반 UUID' 형식으로 고유 사용자 ID 생성
        return username;
    }

    @Override
    public String getProfileImageUrl() {
        return (String) attributes.getOrDefault("profileImageUrl", null);
    }
}
