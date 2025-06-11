package com.shopperstop.user_services.repository;

import com.shopperstop.user_services.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    public User findByUsername(String username);
}
