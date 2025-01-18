package com.goorm.nyangnyam_back.dto.Response;

public interface LoginResponse {
    String getProvider();        //제공자 (Ex. kakao, naver, google, ...)
    String getProviderId();      //제공자에서 발급해주는 아이디(번호)
    String getEmail();           //사용자 이메일
    String getUsername();        //사용자 고유 ID ('제공자 + 이메일 기반 UUID')
    String getProfileImageUrl(); //사용자 이미지
}