package com.goorm.nyangnyam_back.repository;

import com.goorm.nyangnyam_back.entity.DiariesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DiariesRepository extends JpaRepository<DiariesEntity, Long> {

}
