package com.shopperstop.user_services.service;

import com.shopperstop.user_services.entity.User;
import com.shopperstop.user_services.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    public void addNewUser(User user){
        user.setCreatedAt(new Date());
        userRepository.save(user);
    }
    public void saveExistingUser(User user){
        user.setUpdatedAt(new Date());
        userRepository.save(user);
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);

    }

    public boolean deleteUserByUsername(String username) {
        User userToBeDeleted = userRepository.findByUsername(username);
        if (userToBeDeleted != null) {
            userRepository.deleteById(userToBeDeleted.getUser_id());
            return true;
        }
        return false;
    }

}
