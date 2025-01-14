package com.goorm.nyangnyam_back.repository;

import com.goorm.nyangnyam_back.model.BComment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<BComment, Integer> {
    List<BComment> findByBoardId(Integer boardId);
}
