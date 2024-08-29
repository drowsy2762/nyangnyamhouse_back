package com.goorm.nyangnyam_back.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "Diaries")
public class Diaries {
    @Id
    private String id;

    private String images;
    private String comments;
    private String publicRange;
    private String category;
    private String userId;
    private Integer grade;
    private Boolean recommend;

    private Integer likes = 0;
    private Integer scraps = 0;
    private List<Comment> commentList = new ArrayList<>();
}
