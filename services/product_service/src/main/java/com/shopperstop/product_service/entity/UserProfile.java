package com.shopperstop.product_service.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("user_profiles")
@Data
public class UserProfile {

    @Id
    private ObjectId userId;
    private String username;
    private String emailID;
}
