package com.goorm.nyangnyam_back.repository;

import com.goorm.nyangnyam_back.entity.DiariesEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiariesRepository extends MongoRepository<DiariesEntity, Long> {
    List<DiariesEntity> findAllByOrderByModifiedAtDesc();
}