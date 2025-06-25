package com.shopperstop.user_services.entity;

import lombok.Data;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDTO {
    private ObjectId id;
    private String username;
    private String password;
    private String emailID;
    private List<Addresses> addresses = new ArrayList<>();
    private String role;
}

