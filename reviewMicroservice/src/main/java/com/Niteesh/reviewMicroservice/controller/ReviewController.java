package com.Niteesh.reviewMicroservice.controller;

import com.Niteesh.reviewMicroservice.messaging.ReviewMessageProducer;
import com.Niteesh.reviewMicroservice.model.Review;
import com.Niteesh.reviewMicroservice.service.ReviewService;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private ReviewService reviewService;
    private ReviewMessageProducer reviewMessageProducer;

    public ReviewController(ReviewService reviewService,ReviewMessageProducer reviewMessageProducer){
        this.reviewService = reviewService;
        this.reviewMessageProducer = reviewMessageProducer;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId){
        List<Review> reviews = reviewService.getAllReviews(companyId);
        if(reviews == null || reviews.isEmpty()){
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addReview(@RequestParam Long companyId, @RequestBody Review review){
        review.setCompanyId(companyId);
        reviewService.addReview(review);
        reviewMessageProducer.sendMessage(review);
        return new ResponseEntity<>("Review Created",HttpStatus.OK);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId){
        Review review = reviewService.getReviewById(reviewId);
        if(review != null){
            return new ResponseEntity<>(review,HttpStatus.OK);
        }
        else return new ResponseEntity<>(review,HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReviewById(@PathVariable Long reviewId,
                                                   @RequestBody Review review){
        String reviewResponse = reviewService.updateReview(reviewId,review);
        if(reviewResponse.equals("NO ITEM")){
            return new ResponseEntity<>(reviewResponse,HttpStatus.NOT_FOUND);
        }
        else return new ResponseEntity<>(reviewResponse,HttpStatus.OK);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReviewById(@PathVariable Long reviewId){
        String response = reviewService.deleteReviewById(reviewId);
        if(response.equals("REVIEW DELETED SUCCESSFULLY")){
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        else return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }
    @GetMapping("/averageRating")
    public Double getAverageRating(@RequestParam Long companyId){
        List<Review> reviewList = reviewService.getAllReviews(companyId);
        return reviewList.stream().mapToDouble(Review::getRating).average().orElse(0.0);
    }
}
