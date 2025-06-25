package com.shopperstop.user_services.service;

import com.shopperstop.user_services.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserEventProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topics.user-topic}")
    private String userTopic;

    public void sendUserCreatedEvent(User user) {
        Map<String, Object> event = Map.of(
                "userId", user.getUser_id().toString(),
                "username", user.getUsername(),
                "emailID", user.getEmailID()
        );
        kafkaTemplate.send(userTopic, "UserCreated", event);
    }

    public void sendUserUpdatedEvent(User user) {
        Map<String, Object> event = Map.of(
                "userId", user.getUser_id().toString(),
                "username", user.getUsername(),
                "emailID", user.getEmailID()
        );
        kafkaTemplate.send(userTopic, "UserUpdated", event);
    }

    public void sendUserDeletedEvent(User user) {
        Map<String, Object> event = Map.of(
                "userId", user.getUser_id().toString(),
                "username", user.getUsername(),
                "emailID", user.getEmailID()
        );
        kafkaTemplate.send(userTopic,"UserDeleted", event);
    }
}
