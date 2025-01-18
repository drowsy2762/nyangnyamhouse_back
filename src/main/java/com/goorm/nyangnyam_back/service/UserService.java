package com.goorm.nyangnyam_back.service;

import com.goorm.nyangnyam_back.document.User;
import com.goorm.nyangnyam_back.dto.Response.LoginResponse;
import com.goorm.nyangnyam_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User registerUser(LoginResponse loginResponse) {
        System.out.println("ㅡㅡ 사용자 회원가입 ㅡㅡ");
        User user = new User();
        user.setUsername(loginResponse.getUsername());
        user.setProvider(loginResponse.getProvider());
        user.setEmail(loginResponse.getEmail());
        user.setProfileImageUrl(loginResponse.getProfileImageUrl());

        // 서비스에서 추가 입력 받아야 할 필드
        user.setRole("ROLE_USER");
        user.setNickname("화난 라이언");  // ex) 화난 라이언, 건방진 제이지 ...
        //user.setMajor();

        return userRepository.save(user);
    }

    public Optional<User> findUserByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    public Optional<User> findUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    public Optional<User> findUserById(String id) {
        return userRepository.findById(id);
    }
}