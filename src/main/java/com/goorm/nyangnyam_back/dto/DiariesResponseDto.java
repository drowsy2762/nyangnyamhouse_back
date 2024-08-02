package com.goorm.nyangnyam_back.dto;

import com.goorm.nyangnyam_back.entity.DiariesEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class DiariesResponseDto {
    private String comment;
    private String userId;
    private String publicRange;
    private String category;
    private Long id;
    private Integer grade;
    private LocalDateTime createAt;
    private Boolean recommend;

    public DiariesResponseDto(DiariesEntity entity) {
        this.id = entity.getId();
        this.comment = entity.getComment();
        this.publicRange = entity.getPublicRange();
        this.grade = entity.getGrade();
        this.recommend = entity.getRecommend();
        this.category = entity.getCategory();
        this.createAt =entity.getCreatedAt();
        this.userId = getUserId();
    }
}
