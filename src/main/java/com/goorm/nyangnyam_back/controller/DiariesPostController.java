package com.goorm.nyangnyam_back.controller;

import com.goorm.nyangnyam_back.dto.DiariesResponseDto;
import com.goorm.nyangnyam_back.repository.DiariesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/diaries/{userId}")
public class DiariesPostController {

    @GetMapping("/posts")
    public List<DiariesResponseDto> getPosts() {
        return this.getPosts();
    }
    
}
