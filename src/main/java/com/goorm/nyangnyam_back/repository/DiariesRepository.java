package com.goorm.nyangnyam_back.repository;

import com.goorm.nyangnyam_back.dto.DiariesResponseDto;
import com.goorm.nyangnyam_back.entity.DiariesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiariesRepository extends JpaRepository<DiariesEntity, Long> {
    List<DiariesResponseDto> findAllByOrderByModifiedAtDesc();
}
