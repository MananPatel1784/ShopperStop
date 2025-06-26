package com.shopperstop.product_service.service;

import com.shopperstop.product_service.entity.Review;
import com.shopperstop.product_service.entity.ReviewDTO;
import com.shopperstop.product_service.entity.UserProfile;
import com.shopperstop.product_service.repository.ReviewRepository;
import com.shopperstop.product_service.repository.UserProfileRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    public Review addReview(ReviewDTO reviewDTO, String username, ObjectId productID) throws Exception {
       UserProfile userProfile = userProfileRepository.findByUsername(username);
        if (userProfile == null) {
            throw new Exception("User not found");
        }
        Review review = new Review();
        review.setComment(reviewDTO.getComment());
        review.setRating(reviewDTO.getRating());
        review.setProductId(productID);
        review.setUserId(userProfile.getUserId());
        review.setUsername(userProfile.getUsername());
        review.setCreatedAt(new Date());

        return reviewRepository.save(review);
    }

    public Review updateReview(ReviewDTO reviewDTO, ObjectId reviewId) throws Exception {
        Optional<Review> reviewOpt = reviewRepository.findById(reviewId);
        if (reviewOpt.isEmpty()) {
            throw new Exception("Review not found");
        }
        Review review = reviewOpt.get();
        review.setComment(reviewDTO.getComment());
        review.setRating(reviewDTO.getRating());

        return reviewRepository.save(review);
    }

    public void deleteReview(ObjectId reviewId) throws Exception {
        if (!reviewRepository.existsById(reviewId)) {
            throw new Exception("Review not found");
        }
        reviewRepository.deleteById(reviewId);
    }

    public List<Review> getByPID(ObjectId PID) throws Exception{
        return reviewRepository.findByProductId(PID);
    }
}

