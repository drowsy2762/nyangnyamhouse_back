package com.goorm.nyangnyam_back.service;

import com.goorm.nyangnyam_back.dto.DiariesResponseDto;
import com.goorm.nyangnyam_back.repository.DiariesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiariesService {
    private final DiariesRepository diariesRepository;

    @Transactional(readOnly = true)
    public List<DiariesResponseDto> getPosts(){
        return diariesRepository.findAllByOrderByModifiedAtDesc().stream().map(DiariesResponseDto::new).toList();
    }
}
