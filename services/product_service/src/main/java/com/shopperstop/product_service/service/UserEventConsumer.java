package com.shopperstop.product_service.service;

import com.shopperstop.product_service.entity.UserProfile;
import com.shopperstop.product_service.repository.UserProfileRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.springframework.kafka.support.KafkaHeaders;

import java.util.Map;

@Service
public class UserEventConsumer {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @KafkaListener(topics = "${kafka.topics.user-topic}", groupId = "product-service-group")
    public void consumeUserEvent(
            @Header(name = KafkaHeaders.RECEIVED_KEY, required = false) String eventType,
            Map<String, Object> event) {

        System.out.println("Received EventType: " + eventType);
        System.out.println("Payload: " + event);

        ObjectId userId = new ObjectId(event.get("userId").toString());

        switch (eventType) {
            case "UserCreated":
                UserProfile userProfile = new UserProfile();
                userProfile.setUserId(userId);
                userProfile.setUsername(event.get("username").toString());
                userProfile.setEmailID(event.get("emailID").toString());
                userProfileRepository.save(userProfile);
                break;
            case "UserUpdated":
                String username = (String) event.get("username");
                String emailID = (String) event.get("emailID");

                UserProfile updatedUserProfile = userProfileRepository.findById(userId).get();
                updatedUserProfile.setUserId(userId);
                updatedUserProfile.setUsername(username);
                updatedUserProfile.setEmailID(emailID);

                userProfileRepository.save(updatedUserProfile);
                break;

            case "UserDeleted":
                userProfileRepository.deleteById(userId);
                break;

            default:
                System.out.println("Unknown Event Type: " + eventType);
        }
    }

}
