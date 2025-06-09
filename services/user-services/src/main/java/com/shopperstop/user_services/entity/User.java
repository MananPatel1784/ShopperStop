package com.shopperstop.user_services.entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "users")
@Data
public class User {

    @Id
    private ObjectId user_id;
    @Indexed(unique = true)
    @NonNull
    private String username;
    @Indexed(unique = true)
    @NonNull
    private String emailID;
    @NonNull
    private String password;
    private UserRole userRole;
    private Date createdAt;
    private Date updatedAt;
    @DBRef
    private List<Addresses> addresses = new ArrayList<>();

}
