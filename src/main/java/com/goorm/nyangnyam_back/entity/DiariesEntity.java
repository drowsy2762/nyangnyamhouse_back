package com.goorm.nyangnyam_back.entity;

import com.goorm.nyangnyam_back.dto.DiariesRequestsDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class DiariesEntity extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private String images;

    @Column(nullable = false)
    private String publicRange;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Integer grade;

    @Column(nullable = false)
    private Boolean recommend;

    @Column(nullable = false)
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