package com.shopperstop.product_service.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Objects;

@Document("reviews")
@Data
public class Review {
    @Id
    private ObjectId id;
    private ObjectId productId;
    private ObjectId userId;
    private String username;
    private String comment;
    private int rating; // 1-5
    private Date createdAt;
}
