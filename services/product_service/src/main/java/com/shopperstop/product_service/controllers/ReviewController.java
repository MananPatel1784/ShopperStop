package com.shopperstop.product_service.controllers;

import com.shopperstop.product_service.entity.Review;
import com.shopperstop.product_service.entity.ReviewDTO;
import com.shopperstop.product_service.service.ReviewService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/create/{username}/{PID}")
    public ResponseEntity<?> createReview(@RequestBody ReviewDTO reviewDTO, @PathVariable String username, @PathVariable ObjectId PID) {
        try {
            Review saved = reviewService.addReview(reviewDTO, username, PID);
            if (saved != null) return new ResponseEntity<Review>(saved, HttpStatus.CREATED);
            else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-review/{PID}")
    public ResponseEntity<?> getReviewById(@PathVariable ObjectId PID){
        try {
            List<Review> reviewList = reviewService.getByPID(PID);
            if(reviewList != null){
                return new ResponseEntity<List<Review>>(reviewList, HttpStatus.OK);
            }
            else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{reviewId}")
    public ResponseEntity<?> updateReview(@RequestBody ReviewDTO reviewDTO, @PathVariable ObjectId reviewId){
        try {
            Review updated = reviewService.updateReview(reviewDTO, reviewId);
            return new ResponseEntity<Review>(updated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable ObjectId reviewId){
        try {
            reviewService.deleteReview(reviewId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
