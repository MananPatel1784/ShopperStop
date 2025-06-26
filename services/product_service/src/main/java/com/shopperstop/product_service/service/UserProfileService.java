package com.shopperstop.product_service.service;

import com.shopperstop.product_service.entity.UserProfile;
import com.shopperstop.product_service.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    public UserProfile findUserByUsername(String username){
        return userProfileRepository.findByUsername(username);
    }
}
