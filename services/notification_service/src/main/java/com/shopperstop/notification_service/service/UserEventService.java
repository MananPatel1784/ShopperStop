package com.shopperstop.notification_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserEventService {

    @Autowired
    private EmailService emailService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "${kafka.topics.user-topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeUserEvent(
            @Header(name = KafkaHeaders.RECEIVED_KEY, required = false) String eventType,
            Map<String, Object> event) {

        try {


            System.out.println("Received EventType: " + eventType);
            System.out.println("Payload: " + event);

            String username = (String) event.get("username");
            String emailID = (String) event.get("emailID");
            String userIdStr = (String) event.get("userId");

            if (username == null || emailID == null || userIdStr == null) {
                System.out.println("Invalid Event Data");
                return;
            }

            ObjectId userId = new ObjectId(userIdStr);

            switch (eventType) {
                case "UserCreated":
                    String welcomeMessage = "Hello " + username + ",\nWelcome to ShopperStop";
                    emailService.setJavaMailSender(emailID, "Welcome to ShopperStop", welcomeMessage);
                    System.out.println("User Created mail sent");
                    break;

                case "UserUpdated":
                    String updateMessage = "Hello " + username + ",\nYour credentials have been updated";
                    emailService.setJavaMailSender(emailID, "Update from ShopperStop", updateMessage);
                    System.out.println("User Updated mail sent");
                    break;

                case "UserDeleted":
                    String deleteMessage = "Goodbye " + username + ",\nHope to see you soon back on ShopperStop";
                    emailService.setJavaMailSender(emailID, "Goodbye from ShopperStop", deleteMessage);
                    System.out.println("User Deleted mail sent");
                    break;

                default:
                    System.out.println("Unknown Event Type: " + eventType);
            }

        } catch (Exception e) {
            System.out.println("Error processing event: " + e.getMessage());
        }
    }
}
