package com.goorm.nyangnyam_back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("dev") // dev 프로필에서만 활성화
@EnableWebSecurity
public class DevSecurityConfig {

    @Bean
    public SecurityFilterChain devFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((auth) -> auth.disable()) // CSRF 비활성화
                .authorizeHttpRequests((auth) -> auth.anyRequest().permitAll()) // 모든 요청 허용
                .formLogin((auth) -> auth.disable()) // 폼 로그인 비활성화
                .httpBasic((auth) -> auth.disable()); // HTTP Basic 인증 비활성화

        return http.build();
    }
}