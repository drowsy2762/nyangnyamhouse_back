package com.goorm.nyangnyam_back.controller;

import com.goorm.nyangnyam_back.dto.DiariesRequestsDto;
import com.goorm.nyangnyam_back.dto.DiariesResponseDto;
import com.goorm.nyangnyam_back.dto.DiariesSuccessResponseDto;
import com.goorm.nyangnyam_back.service.DiariesService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/diaries/{userId}")
public class DiariesPostController {

    private final DiariesService diariesService;

    public DiariesPostController(DiariesService diariesService) {
        this.diariesService = diariesService;
    }

    @GetMapping("/")
    public List<DiariesResponseDto> getPosts() {
        return diariesService.getPosts();
    }

    @PostMapping("/")
    public DiariesResponseDto createPost(@RequestBody DiariesRequestsDto requestsDto) {
        return diariesService.createPost(requestsDto);
    }

    @Transactional
    @GetMapping("/{id}")
    public DiariesResponseDto getPost(@PathVariable ObjectId id) {
        return diariesService.getPost(id);
    }

    @PutMapping("/{id}")
    public DiariesResponseDto updatePost(@PathVariable ObjectId id, @RequestBody DiariesRequestsDto requestsDto) throws Exception {
        return diariesService.updatePost(id, requestsDto);
    }

    @DeleteMapping("/{id}")
    public DiariesSuccessResponseDto deletePost(@PathVariable ObjectId id, @RequestBody DiariesRequestsDto requestsDto) throws Exception {
        return diariesService.deletePost(id, requestsDto);
    }
}