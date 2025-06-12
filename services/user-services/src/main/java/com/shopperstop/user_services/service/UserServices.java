package com.shopperstop.user_services.service;

import com.shopperstop.user_services.entity.User;
import com.shopperstop.user_services.entity.UserRole;
import com.shopperstop.user_services.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void addNewUser(User user){
        try {
            user.setCreatedAt(new Date());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setUserRole(UserRole.CUSTOMER);
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while saving the user");
        }

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
