package com.Niteesh.companyMicroservice.client;

import com.Niteesh.companyMicroservice.external.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "REVIEWMICROSERVICE",url="${review-service.url}")
public interface ReviewClient {
    @GetMapping("/reviews")
    List<Review> getReviews(@RequestParam Long companyId);

    @GetMapping("/reviews/averageRating")
    Double getAverageRatingForCompany(@RequestParam Long companyId);
}
