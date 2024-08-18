package com.goorm.nyangnyam_back.dto.Response;

public interface LoginResponse {
    String getProvider();    //제공자 (Ex. kakao, naver, google, ...)
    String getProviderId();  //제공자에서 발급해주는 아이디(번호)
    String getEmail();       //사용자이메일
    String getName();    //사용자 실명
}