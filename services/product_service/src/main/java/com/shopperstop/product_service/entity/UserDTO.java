package com.shopperstop.product_service.entity;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class UserDTO {
    private ObjectId id;
    private String username;
    private String role;
}

