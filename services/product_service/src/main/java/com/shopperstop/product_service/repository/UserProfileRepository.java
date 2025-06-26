package com.shopperstop.product_service.repository;

import com.shopperstop.product_service.entity.UserProfile;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserProfileRepository extends MongoRepository<UserProfile, ObjectId> {
    public UserProfile findByUsername(String username);
}

