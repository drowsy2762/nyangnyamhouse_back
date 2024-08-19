package com.goorm.nyangnyam_back.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "Refresh")
@Getter
@Setter
@ToString
public class Refresh {
    @Id
    private String id;
    private String username;
    private String refresh;
    private String expiration;
}
