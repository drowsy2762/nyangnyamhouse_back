package com.goorm.nyangnyam_back.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    // id는 MongoDB에서 자동 생성
    @Id
    private String id;

    // 다이어리의 제목, 내용, 이미지, 댓글, 공개 범위, 카테고리, 사용자 아이디, 평점, 추천 여부, 작성 시간
    private String images;
    private String comments;
    private String publicRange;
    private String category;
    private String userId;
    private Integer grade;
    private Boolean recommend;

    // 초기 생성시 likes, scraps는 0으로 초기화
    private Integer likes = 0;
    private Integer scraps = 0;

    @JsonProperty("comment_list")
    private List<DComment> commentList = new ArrayList<>();

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();
}