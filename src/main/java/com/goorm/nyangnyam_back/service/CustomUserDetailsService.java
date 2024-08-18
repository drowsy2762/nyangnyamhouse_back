package com.goorm.nyangnyam_back.service;


import com.goorm.nyangnyam_back.document.User;
import com.goorm.nyangnyam_back.dto.CustomUserDetails;
import com.goorm.nyangnyam_back.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * CustomUserDetailsService 클래스
 *
 * 코드 작성자:
 *      -서진영(jin2304)
 *
 * 코드 설명:
 *      -CustomUserDetailsService 클래스는 UserDetailsService를 구현한 커스텀 서비스.
 *       사용자 인증을 위해 DB에서 사용자 정보를 로드하고, 사용자 정보를 스프링 시큐리티에서
 *       사용할 수 있도록 사용자 정보를 담은 UserDetails 객체로 변환하여 반환.
 *
 * 코드 주요 기능:
 *      -loadUserByUsername(): 사용자의 username을 받아서 DB에서 사용자 정보를 조회.
 *      -사용자가 존재하지 않으면 UsernameNotFoundException 예외를 발생.
 *      -사용자가 존재하면 CustomUserDetails 객체에 사용자 정보를 담아 반환.
 *
 * 코드 작성일:
 *      -2024.08.18 ~ 2024.08.18
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // DB에서 사용자 조회
        User user = userRepository.findByUsername(username);
        if (user == null) { // 사용자가 존재하지 않는다면
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // 사용자가 존재한다면 CustomUserDetails에 담아서 반환
        return new CustomUserDetails(user);
    }
}
