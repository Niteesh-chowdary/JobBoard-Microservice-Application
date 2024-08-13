package com.Niteesh.companyMicroservice.dto;

import com.Niteesh.companyMicroservice.external.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyWithReviewDTO {
    private Long id;
    private String name;
    private String description;
    private String location;
    private Double companyRating;
    List<Review> reviews;
}
