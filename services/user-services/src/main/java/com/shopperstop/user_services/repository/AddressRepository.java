package com.shopperstop.user_services.repository;

import com.shopperstop.user_services.entity.Addresses;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AddressRepository extends MongoRepository<Addresses, ObjectId> {
}
