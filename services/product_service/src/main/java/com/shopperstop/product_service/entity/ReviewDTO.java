package com.shopperstop.product_service.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class ReviewDTO {
    private ObjectId id;
    private String comment;
    private int rating; // 1-5
    private Date createdAt;
}
