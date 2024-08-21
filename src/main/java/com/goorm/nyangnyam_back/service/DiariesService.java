package com.goorm.nyangnyam_back.service;

import com.goorm.nyangnyam_back.dto.DiariesRequestsDto;
import com.goorm.nyangnyam_back.dto.DiariesResponseDto;
import com.goorm.nyangnyam_back.dto.DiariesSuccessResponseDto;
import com.goorm.nyangnyam_back.entity.DiariesEntity;
import com.goorm.nyangnyam_back.repository.DiariesRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiariesService {
    private final DiariesRepository diariesRepository;

    @Transactional(readOnly = true)
    public List<DiariesResponseDto> getPosts() {
        return diariesRepository.findAllByOrderByModifiedAtDesc().stream()
                .map(DiariesResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public DiariesResponseDto createPost(DiariesRequestsDto requestsDto) {
        DiariesEntity entity = new DiariesEntity(requestsDto);
        diariesRepository.save(entity);
        return new DiariesResponseDto(entity);
    }

    @Transactional
    public DiariesResponseDto getPost(ObjectId id) {
        return diariesRepository.findById(id).map(DiariesResponseDto::new).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다")
        );
    }

    @Transactional
    public DiariesResponseDto updatePost(ObjectId id, DiariesRequestsDto requestsDto) throws Exception {
        DiariesEntity diaries = diariesRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다")
        );
        // 토큰으로 처리할 예정

        diaries.update(requestsDto);
        return new DiariesResponseDto(diaries);
    }

    @Transactional
    public DiariesSuccessResponseDto deletePost(ObjectId id, DiariesRequestsDto requestsDto) throws Exception {
        DiariesEntity diaries = diariesRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다")
        );

        diariesRepository.deleteById(id);
        return new DiariesSuccessResponseDto(true);
    }
}