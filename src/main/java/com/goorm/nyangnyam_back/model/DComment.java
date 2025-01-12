package com.goorm.nyangnyam_back.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

// Diaries의 Comment를 나타내는 DComment 클래스
public class DComment {
    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("text")
    private String text;

    @JsonProperty("time_stamp")
    private LocalDateTime timeStamp = LocalDateTime.now();
}