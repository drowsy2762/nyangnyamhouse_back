package com.goorm.nyangnyam_back.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "User")
@Getter
@Setter
@ToString
@NoArgsConstructor  //기본 생성자
public class User {

    @Id
    private String _id;         // MongoDB의 기본 _id 필드에 대응하는 필드
    private String username;    // 소셜 로그인 제공자 + 소셜 로그인 고유 아이디로 만든 새로운 아이디
    private String provider;    // 소셜 로그인 제공자 (예: "kakao", "google", "facebook")
    private String providerId;  // 소셜 로그인 고유 아이디
    private String email;       // 사용자 이메일
    private String name;        // 사용자 본명
    private String role;        // 사용자 권한
}
