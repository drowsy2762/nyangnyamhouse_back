package com.goorm.nyangnyam_back.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Comment {
    @JsonProperty("userId")
    private String userId;

    @JsonProperty("text")
    private String text;

    @JsonProperty("timeStamp")
    private String timeStamp;

    // 생성자, getter, setter 생략
}