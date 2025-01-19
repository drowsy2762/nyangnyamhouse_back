package com.goorm.nyangnyam_back.config;

import com.goorm.nyangnyam_back.jwt.*;
import com.goorm.nyangnyam_back.repository.RefreshRepository;
import com.goorm.nyangnyam_back.repository.UserRepository;
import com.goorm.nyangnyam_back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@Profile("!dev")
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserRepository userRepository;
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final UserService userService;




    //AuthenticationManager 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //csrf disable
        http
                .csrf((auth) -> auth.disable());

        //From 로그인 방식 disable
        http
                .formLogin((auth) -> auth.disable());

        //http basic 인증 방식 disable
        http
                .httpBasic((auth) -> auth.disable());

        //경로별 인가 작업 설정
        http
                .authorizeHttpRequests((auth) -> auth
                        // 로그인 및 루트 경로는 모두 허용
                        .requestMatchers("/auth/login", "/").permitAll()

                        // 상세,목록 가져오기(GET)은 모두 허용
                        .requestMatchers(HttpMethod.GET, "/boards/**").permitAll()

                        // 생성,수정,삭제,좋아요 같은 작업은 인증된 사용자만 허용
                        .requestMatchers(HttpMethod.POST, "/boards/**").authenticated() // 생성
                        .requestMatchers(HttpMethod.PUT, "/boards/**").authenticated()  // 수정
                        .requestMatchers(HttpMethod.DELETE, "/boards/**").authenticated() // 삭제
                        //.requestMatchers(HttpMethod.POST, "/boards/{id}/like").authenticated() // 좋아요

                        // ADMIN 전용 경로
                        .requestMatchers("/admin").hasRole("ADMIN")

                        // 기타 모든 요청은 인증된 사용자만 허용
                        .anyRequest().authenticated()
                );


        //세션 설정 (무상태 설정)
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        //CustomAuthenticationProvider 등록
        http
                .authenticationProvider(customAuthenticationProvider);


        //LoginFilter 등록
        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), userService, jwtUtil, refreshRepository), UsernamePasswordAuthenticationFilter.class);


        //JWTFilter 등록
        http
                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);


        //CustomLogoutFilter 등록
        http
                .addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshRepository), LogoutFilter.class);


        return http.build();
    }
}