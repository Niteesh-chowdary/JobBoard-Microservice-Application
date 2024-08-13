package com.Niteesh.companyMicroservice.service;

import com.Niteesh.companyMicroservice.client.ReviewClient;
import com.Niteesh.companyMicroservice.dto.CompanyWithReviewDTO;
import com.Niteesh.companyMicroservice.dto.ReviewMessage;
import com.Niteesh.companyMicroservice.external.Review;
import com.Niteesh.companyMicroservice.mapper.CompanyMapper;
import com.Niteesh.companyMicroservice.model.Company;
import com.Niteesh.companyMicroservice.repository.CompanyRepository;
import feign.FeignException;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private ReviewClient reviewClient;

    public CompanyService(CompanyRepository repository,ReviewClient reviewClient){
        this.companyRepository = repository;
        this.reviewClient = reviewClient;
    }
    public CompanyWithReviewDTO updateCompanyWithReviewDTO(Company company){
        List<Review> reviews = new ArrayList<>();
        try {
            reviews = reviewClient.getReviews(company.getId());
        } catch (FeignException.NotFound e) {
            // 404 Not Found - handle it by using an empty list
            reviews = new ArrayList<>();
        }
        return CompanyMapper.mapCompanyWithReviewDTO(company,reviews);
    }
    public List<CompanyWithReviewDTO> findAll() {
        List<Company> companies = companyRepository.findAll();
        List<CompanyWithReviewDTO> companyWithReviewDTOS = new ArrayList<>();
        for(Company company:companies){
            CompanyWithReviewDTO companyWithReviewDTO = updateCompanyWithReviewDTO(company);
            companyWithReviewDTOS.add(companyWithReviewDTO);
        }
        return companyWithReviewDTOS;
    }

    public String createCompany(Company company) {
        Company companyCreated = companyRepository.save(company);
        return "COMPANY CREATED";
    }

    public CompanyWithReviewDTO findCompanyById(Long companyId) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if(company == null) return null;
        return updateCompanyWithReviewDTO(company);
    }

    public void deleteCompanyById(Long companyId) {
        companyRepository.deleteById(companyId);
    }

    public String updateCompanyById(Long companyId, Company company) {
        Company companyCreated = companyRepository.findById(companyId).orElse(null);
        if(companyCreated != null) {
            companyRepository.save(company);
            return "COMPANY UPDATED";
        }
        else return "COMPANY UPDATE FAILED";
    }

    public void updateCompanyRating(ReviewMessage reviewMessage) {
        Company company = companyRepository.findById(reviewMessage.getCompanyId()).orElseThrow(()-> new NotFoundException("company not found"+reviewMessage.getCompanyId()));
        Double averageRating = reviewClient.getAverageRatingForCompany(company.getId());
        company.setCompanyRating(averageRating);
        companyRepository.save(company);
    }
}
