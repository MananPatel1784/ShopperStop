package com.shopperstop.product_service.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("reviews")
@Data
public class Review {
    @Id
    private String id;
    private String productId;
    private String userId;
    private String comment;
    private int rating; // 1-5
    private Date createdAt;
}
