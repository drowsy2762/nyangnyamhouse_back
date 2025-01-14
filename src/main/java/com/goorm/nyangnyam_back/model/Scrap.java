package com.goorm.nyangnyam_back.model;

import com.goorm.nyangnyam_back.document.User;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Data
@Document(collection = "Scrap")
public class Scrap {
    @Id
    private String id;

    @DBRef
    private User user;

    @DBRef
    private GroupOrder groupOrder;

    private Date createdAt;

    public Scrap() {
        this.createdAt = new Date();
    }
}