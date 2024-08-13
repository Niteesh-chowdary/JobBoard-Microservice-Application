package com.Niteesh.jobMicroservice.mapper;

import com.Niteesh.jobMicroservice.dto.JobWithCompanyDTO;
import com.Niteesh.jobMicroservice.external.Company;
import com.Niteesh.jobMicroservice.external.Review;
import com.Niteesh.jobMicroservice.model.Job;

import java.util.List;

public class JobMapper {

    public static JobWithCompanyDTO mapToJobWithCompanyDTO(Job job, Company company, List<Review> reviewList){
        JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();
        jobWithCompanyDTO.setId(job.getId());
        jobWithCompanyDTO.setDescription(job.getDescription());
        jobWithCompanyDTO.setLocation(job.getLocation());
        jobWithCompanyDTO.setTitle(job.getTitle());
        jobWithCompanyDTO.setMaxSalary(job.getMaxSalary());
        jobWithCompanyDTO.setMinSalary(job.getMinSalary());
        jobWithCompanyDTO.setCompany(company);
        jobWithCompanyDTO.getCompany().setReviewList(reviewList);
        return jobWithCompanyDTO;
    }
}
