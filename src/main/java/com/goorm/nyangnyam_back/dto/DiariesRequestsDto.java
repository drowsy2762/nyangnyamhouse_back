package com.goorm.nyangnyam_back.dto;

import lombok.Getter;

@Getter
public class DiariesRequestsDto {
    private String images;
    private String comments;
    private String publicRange;
    private String category;
    private Integer grade;
    private Boolean recommend;
}
