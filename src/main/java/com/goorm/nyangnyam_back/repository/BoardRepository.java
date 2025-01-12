package com.goorm.nyangnyam_back.repository;

import com.goorm.nyangnyam_back.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends MongoRepository<Board, Integer> {

    Page<Board> findByTitleContaining(String searchkeyword, Pageable pageable);
}
