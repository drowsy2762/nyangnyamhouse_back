package com.goorm.nyangnyam_back.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@ToString
@Document(collection = "Board")
public class Board {

    @Id
    private String id;
    private String username;
    private String title;
    private String content;
    private String publicRange; // 공개 범위 (예: 공개, 비공개)
    private String category;    // 카테고리 (예: 한식, 중식, 양식 등)

    private String comments;
    private Integer likesCount = 0; // 좋아요 수
    private Integer scrapsCount = 0;
}