package com.shopperstop.product_service.repository;

import com.shopperstop.product_service.entity.Review;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, ObjectId> {
    List<Review> findByProductId(ObjectId productId);
    List<Review> findByUserId(ObjectId userId);
}