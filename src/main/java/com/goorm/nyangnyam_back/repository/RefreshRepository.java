package com.goorm.nyangnyam_back.repository;


import com.goorm.nyangnyam_back.document.Refresh;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RefreshRepository extends MongoRepository<Refresh, String> {

    //refresh 토큰 존재 여부 확인 메서드
    Boolean existsByRefresh(String refresh);

    //refresh 토큰 삭제 메서드
    @Transactional
    void deleteByRefresh(String refresh);
}