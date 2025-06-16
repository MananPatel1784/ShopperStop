package com.shopperstop.user_services.controllers;

import com.shopperstop.user_services.entity.Addresses;
import com.shopperstop.user_services.entity.User;
import com.shopperstop.user_services.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserActionController {

    @Autowired
    private UserServices userServices;

    @PutMapping("/update-user")
    public ResponseEntity<?> updateUser(@RequestBody User updatedUserInfo){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User temp = userServices.getUserByUsername(username);
            if(temp != null){
                if(updatedUserInfo.getUsername() != null && !updatedUserInfo.getUsername().isEmpty())
                    temp.setUsername(updatedUserInfo.getUsername());

                if(updatedUserInfo.getEmailID() != null && !updatedUserInfo.getEmailID().isEmpty())
                    temp.setEmailID(updatedUserInfo.getEmailID());

                if(updatedUserInfo.getPassword() != null && !updatedUserInfo.getPassword().isEmpty())
                    temp.setPassword(updatedUserInfo.getPassword());

                userServices.saveExistingUser(temp);

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
            if(userServices.deleteUserByUsername(username)){
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
            String username = authentication.getName();
            User user = userServices.getUserByUsername(username);
            if(user != null) return new ResponseEntity<User>(user, HttpStatus.OK);
            else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
