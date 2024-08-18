package com.goorm.nyangnyam_back.dto;

import com.goorm.nyangnyam_back.document.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


/**
 * CustomUserDetails 클래스
 *
 * 코드 작성자:
 *      -서진영(jin2304)
 *
 * 코드 설명:
 *      -CustomUserDetails 클래스는 User 객체를 기반으로 사용자 정보를 스프링 시큐리티에서 사용할 수 있는 형태로 제공.
 *
 * 코드 주요 기능:
 *      사용자의 권한, 아이디, 비밀번호 등 필요한 정보를 UserDetails 인터페이스의 메서드를 통해 반환.
 *
 * 코드 작성일:
 *      -2024.08.18 ~ 2024.08.18
 */
public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }


    /**
     *  사용자의 권한 반환
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collection;
    }


    /**
     * 아이디 반환
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }


    /**
     * 비밀번호 반환
     */
    @Override
    public String getPassword() {
        return "";
    }


    //임시 설정
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}