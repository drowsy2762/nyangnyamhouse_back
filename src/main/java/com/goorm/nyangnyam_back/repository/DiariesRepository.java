package com.goorm.nyangnyam_back.repository;

import com.goorm.nyangnyam_back.model.Diaries;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DiariesRepository extends MongoRepository<Diaries, String> {
}
