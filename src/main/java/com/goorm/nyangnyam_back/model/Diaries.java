package com.goorm.nyangnyam_back.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "Diaries")
public class Diaries {
    @Id
    private String id;

    private String images;
    private String comments;
    private String publicRange;
    private String category;
    private String userId;
    private Integer grade;
    private Boolean recommend;
    private LocalDateTime timeStamp = LocalDateTime.now();

    // 초기 생성시 likes, scraps는 0으로 초기화
    private Integer likes = 0;
    private Integer scraps = 0;

    @JsonProperty("comment_list")
    private List<Comment> commentList = new ArrayList<>();
}