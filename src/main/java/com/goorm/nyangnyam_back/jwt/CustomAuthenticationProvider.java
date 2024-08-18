package com.goorm.nyangnyam_back.jwt;


import com.goorm.nyangnyam_back.service.CustomUserDetailsService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


/**
 * CustomAuthenticationProvider 클래스
 *
 * 코드 작성자:
 *      서진영(jin2304)
 *
 * 코드 설명:
 *      -CustomAuthenticationProvider는 스프링 시큐리티의 AuthenticationProvider를 구현한 커스텀 인증 제공자.
 *      -소셜 로그인과 같이 비밀번호가 없는 사용자 인증을 처리하기 위해 커스터마이징되었으며,
 *      사용자 정보를 CustomUserDetailsService를 통해 로드하고, 비밀번호 검증 없이 인증을 수행.
 *
 * 코드 주요 기능:
 *      -authenticate(): Authentication 객체를 받아 사용자 인증을 처리함. CustomUserDetailsService를 통해 사용자 정보를 조회하고,
 *      비밀번호 검증을 생략한 채 사용자 인증 토큰을 반환.
        -토큰이 반환되었다는 것은 인증이 성공적으로 완료되었다는 것을 의미.
 *
 *      -supports(): 이 AuthenticationProvider가 특정 인증 타입을 지원하는지 여부를 반환함. 여기서는 UsernamePasswordAuthenticationToken을 지원.
 *
 * 코드 작성일:
 *      -2024.08.18 ~ 2024.08.18
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailsService customUserDetailsService;

    public CustomAuthenticationProvider(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // 전달받은 토큰 정보를 통해 DB에서 사용자 정보 로드
        String username = authentication.getName();
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        // 비밀번호가 없는 소셜 로그인 처리
        // DB에서 로드한 사용자 정보를 바탕으로 인증된 Authentication 객체를 생성하여 반환
        // DB에서 조회한 사용자 정보가 null이 아니면서 예외가 발생하지 않고, 토큰이 반환되었다는 것은 인증이 성공적으로 완료되었다는 것을 의미.
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
