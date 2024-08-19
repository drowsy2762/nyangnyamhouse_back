package com.goorm.nyangnyam_back.config;

import com.goorm.nyangnyam_back.jwt.CustomAuthenticationProvider;
import com.goorm.nyangnyam_back.jwt.JWTFilter;
import com.goorm.nyangnyam_back.jwt.JWTUtil;
import com.goorm.nyangnyam_back.jwt.LoginFilter;
import com.goorm.nyangnyam_back.repository.RefreshRepository;
import com.goorm.nyangnyam_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserRepository userRepository;
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    @Autowired
    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, UserRepository userRepository, CustomAuthenticationProvider customAuthenticationProvide, JWTUtil jwtUtil, RefreshRepository refreshRepository) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.userRepository = userRepository;
        this.customAuthenticationProvider = customAuthenticationProvide;
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
    }


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
                        .requestMatchers("/auth/login", "/").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated());

        //세션 설정 (무상태 설정)
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        //CustomAuthenticationProvider 등록
        http
                .authenticationProvider(customAuthenticationProvider);


        //LoginFilter 등록
        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), userRepository, jwtUtil, refreshRepository), UsernamePasswordAuthenticationFilter.class);


        //JWTFilter 등록
        http
                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);


        return http.build();
    }
}