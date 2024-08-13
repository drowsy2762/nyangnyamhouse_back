package com.goorm.nyangnyam_back.entity;

import com.goorm.nyangnyam_back.dto.DiariesRequestsDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collation = "diaries")
public class DiariesEntity extends Timestamped {
    @Id
    private Long id;
    private String comment;
    private String images;
    private String publicRange;
    private String category;
    private Integer grade;
    private Boolean recommend;
    private String userId;

    public DiariesEntity(DiariesRequestsDto requestsDto) {
        this.comment = requestsDto.getComments();
        this.images = requestsDto.getImages();
        this.publicRange = requestsDto.getPublicRange();
        this.category = requestsDto.getCategory();
        this.grade = requestsDto.getGrade();
        this.recommend = requestsDto.getRecommend();
        this.userId = requestsDto.getUserId();
    }

    public void update(DiariesRequestsDto requestsDto) {
        this.comment = requestsDto.getComments();
        this.images = requestsDto.getImages();
        this.publicRange = requestsDto.getPublicRange();
        this.category = requestsDto.getCategory();
        this.grade = requestsDto.getGrade();
        this.recommend = requestsDto.getRecommend();
    }
}