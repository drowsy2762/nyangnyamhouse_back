package com.goorm.nyangnyam_back.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class DiariesEntity extends Timestamped{
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
}
