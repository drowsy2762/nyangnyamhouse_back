package com.goorm.nyangnyam_back.repository;

import com.goorm.nyangnyam_back.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    //username으로 DB 회원 조회
    User findByUsername(String username);
    //email로 DB 회원 조회
    User findByEmail(String email);
}