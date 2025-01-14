package com.goorm.nyangnyam_back.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// Board 테이블과 매핑되는 Entity 클래스
@Data
@Document(collection = "BComment")
public class BComment {
    @Id
    private Integer id;

    private Board board;
    private String content;
    private String author;

}