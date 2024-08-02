package com.goorm.nyangnyam_back.controller;

import com.goorm.nyangnyam_back.repository.DiariesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/diaries/{userId}/post")
public class DiariesPostController {
    private final DiariesRepository diariesRepository;



}
