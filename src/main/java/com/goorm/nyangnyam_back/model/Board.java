package com.goorm.nyangnyam_back.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@Document(collection = "Board")
public class Board {

    @Id
    private Integer id;
    private String title;
    private String content;
    private String filename;
    private String filepath;
    private String visibility; // 공개 범위 (예: 공개, 비공개)

    private boolean isRestaurant;

    private String category; // 카테고리 (예: 한식, 중식, 양식 등)

    private int likeCount; // 좋아요 수

    private List<BComment> comments;
}