package com.goorm.nyangnyam_back.dto;

import lombok.Getter;

@Getter
public class DiariesSuccessResponseDto {
    private Boolean success;

    public DiariesSuccessResponseDto(Boolean success){
        this.success = success;
    }
}
