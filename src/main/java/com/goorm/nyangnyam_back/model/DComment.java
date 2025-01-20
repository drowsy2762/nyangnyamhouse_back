package com.goorm.nyangnyam_back.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

// Diaries의 Comment를 나타내는 DComment 클래스
@Data
public class DComment {
    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("text")
    private String text;

    @JsonProperty("time_stamp")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timeStamp = LocalDateTime.now();
}