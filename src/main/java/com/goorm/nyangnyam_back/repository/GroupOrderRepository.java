package com.goorm.nyangnyam_back.repository;


import com.goorm.nyangnyam_back.model.GroupOrder;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupOrderRepository extends MongoRepository<GroupOrder, Integer> {
}
