package com.Niteesh.reviewMicroservice.service;


import com.Niteesh.reviewMicroservice.model.Review;
import com.Niteesh.reviewMicroservice.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }
    public List<Review> getAllReviews(Long companyId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        return reviews;
    }

    public void addReview(Review review) {
        reviewRepository.save(review);
    }

    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);
    }

    public String updateReview(Long reviewId, Review review) {
        Review reviewCurr = reviewRepository.findById(reviewId).orElse(null);
        if(reviewCurr == null){
            return "NO ITEM";
        }
        else{
            review.setId(reviewId);
            reviewRepository.save(review);
            return "REVIEW UPDATED";
        }
    }

    public String deleteReviewById(Long reviewId) {
        Review reviewCurr = reviewRepository.findById(reviewId).orElse(null);
        if(reviewCurr == null){
            return "NO ITEM";
        }
        reviewRepository.deleteById(reviewId);
        return "REVIEW DELETED SUCCESSFULLY";
    }
}
