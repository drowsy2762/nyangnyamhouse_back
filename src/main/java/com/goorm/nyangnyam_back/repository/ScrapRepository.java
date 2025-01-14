package com.goorm.nyangnyam_back.repository;

import com.goorm.nyangnyam_back.document.User;
import com.goorm.nyangnyam_back.model.Scrap;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ScrapRepository extends MongoRepository<Scrap, Integer> {
    List<Scrap> findByUser(User user);
}
