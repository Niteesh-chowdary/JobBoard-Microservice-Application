package com.Niteesh.companyMicroservice.mapper;


import com.Niteesh.companyMicroservice.dto.CompanyWithReviewDTO;
import com.Niteesh.companyMicroservice.external.Review;
import com.Niteesh.companyMicroservice.model.Company;

import java.util.List;

public class CompanyMapper {
    public static CompanyWithReviewDTO mapCompanyWithReviewDTO(Company company, List<Review> review){
        CompanyWithReviewDTO companyWithReviewDTO = new CompanyWithReviewDTO();
        companyWithReviewDTO.setId(company.getId());
        companyWithReviewDTO.setName(company.getName());
        companyWithReviewDTO.setLocation(company.getLocation());
        companyWithReviewDTO.setDescription(company.getDescription());
        companyWithReviewDTO.setReviews(review);
        companyWithReviewDTO.setCompanyRating(company.getCompanyRating());
        return companyWithReviewDTO;
    }
}
