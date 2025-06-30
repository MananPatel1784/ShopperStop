package com.shopperstop.notification_service.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("notifications")
@Data
public class Notification {
    @Id
    private ObjectId id;
    private ObjectId userId;
    private String type; // EMAIL, SMS, IN_APP
    private String title;
    private String message;
    private boolean read;
    private Date sentAt;
}

