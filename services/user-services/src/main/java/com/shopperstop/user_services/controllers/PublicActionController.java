package com.shopperstop.user_services.controllers;

import com.shopperstop.user_services.entity.User;
import com.shopperstop.user_services.service.UserEventProducer;
import com.shopperstop.user_services.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PublicActionController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private UserEventProducer userEventProducer;

    @PostMapping("/create-user")
    public ResponseEntity<?> createNewUser(@RequestBody User user){
        try {
            User savedUser = userServices.addNewUser(user);
            userEventProducer.sendUserCreatedEvent(savedUser);
            return new ResponseEntity<User>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<Exception>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/health-check")
    public ResponseEntity<?> healthCheck(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
