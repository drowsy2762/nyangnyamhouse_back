package com.goorm.nyangnyam_back.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class Comment {
    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("text")
    private String text;

    @JsonProperty("time_stamp")
    private LocalDateTime timeStamp = LocalDateTime.now();
}