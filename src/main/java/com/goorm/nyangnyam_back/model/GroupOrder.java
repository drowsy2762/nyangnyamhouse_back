package com.goorm.nyangnyam_back.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "GroupOrder")
public class GroupOrder {
    @Id
    private String id;

    private String content;
    private int teamSize;
    private String deliveryLocation;
    private String menu;
    private String imagePath;
    private String category;
    private Date createdAt = new Date();
    private List<String> participants = new ArrayList<>();
}