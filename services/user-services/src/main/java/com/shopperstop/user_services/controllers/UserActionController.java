package com.shopperstop.user_services.controllers;

import com.shopperstop.user_services.entity.Addresses;
import com.shopperstop.user_services.entity.User;
import com.shopperstop.user_services.entity.UserDTO;
import com.shopperstop.user_services.service.UserEventProducer;
import com.shopperstop.user_services.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/user")
public class UserActionController {

    private static final Logger logger = LoggerFactory.getLogger(UserActionController.class);

    @Autowired
    private UserServices userServices;

    @Autowired
    private UserEventProducer userEventProducer;

    @Value("${internal.service.token}")
    private String internalServiceToken;

    @PutMapping("/update-user")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO updatedUserInfo){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User temp = userServices.getUserByUsername(username);
            if(temp != null){

                User saved = userServices.saveExistingUser(updatedUserInfo, username);
                userEventProducer.sendUserUpdatedEvent(saved);

                return new ResponseEntity<User>(temp, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace(); // for debugging
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUserByUsername(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User userToBeDeleted = userServices.getUserByUsername(username);
            if(userToBeDeleted != null) {
                userServices.deleteUserByUsername(username);
                userEventProducer.sendUserDeletedEvent(userToBeDeleted);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-user-details")
    public ResponseEntity<?> getUserDetails(){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            logger.debug("Authentication principal: " + authentication);
            logger.debug("Username from Authentication: " + authentication.getName());

            String username = authentication.getName();

            User user = userServices.getUserByUsername(username);
            if(user != null) {
                return new ResponseEntity<User>(user, HttpStatus.OK);
            }
            return new ResponseEntity<String>("User not found.", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error retrieving user.", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username, @RequestHeader("X-Service-Token") String serviceToken){
        if (!internalServiceToken.equals(serviceToken)) {
            return new ResponseEntity<String>("Token not matching", HttpStatus.UNAUTHORIZED);
        }

        UserDTO user = userServices.getByUsername(username);
        if (user != null) {
            return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
