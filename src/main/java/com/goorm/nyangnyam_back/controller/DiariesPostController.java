package com.goorm.nyangnyam_back.controller;

import com.goorm.nyangnyam_back.dto.DiariesRequestsDto;
import com.goorm.nyangnyam_back.dto.DiariesResponseDto;
import com.goorm.nyangnyam_back.repository.DiariesRepository;
import com.goorm.nyangnyam_back.service.DiariesService;
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
        return this.getPosts();
    }

    @PostMapping("/")
    public DiariesResponseDto createPost(@RequestBody DiariesRequestsDto requestsDto) {
        return  diariesService.createPost(requestsDto);
    }

    @Transactional
    public DiariesResponseDto getPost(@PathVariable Long id){
        return diariesService.getPost(id);
    }

    @PutMapping("/")
    public DiariesResponseDto updatePost(@PathVariable Long id, @RequestBody DiariesRequestsDto requestsDto) throws Exception {
        return diariesService.updatePost(id, requestsDto);
    }
    
}
