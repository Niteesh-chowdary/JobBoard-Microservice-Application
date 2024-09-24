package com.Niteesh.jobMicroservice.service;


import com.Niteesh.jobMicroservice.client.CompanyClient;
import com.Niteesh.jobMicroservice.client.ReviewClient;
import com.Niteesh.jobMicroservice.dto.JobWithCompanyDTO;
import com.Niteesh.jobMicroservice.external.Company;
import com.Niteesh.jobMicroservice.external.Review;
import com.Niteesh.jobMicroservice.mapper.JobMapper;
import com.Niteesh.jobMicroservice.model.Job;
import com.Niteesh.jobMicroservice.repository.JobRepository;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {
    JobRepository repository;

    private CompanyClient companyClient;
    private ReviewClient reviewClient;

    public JobService(JobRepository repository,CompanyClient companyClient,ReviewClient reviewClient){
        this.repository = repository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

    public List<Review> getReviewList(Company company){
        List<Review> reviews = new ArrayList<>();
        if(company == null) return reviews;
        try {
            reviews = reviewClient.getReviews(company.getId());
        } catch (FeignException.NotFound e) {
            // 404 Not Found - handle it by using an empty list
            reviews = new ArrayList<>();
        }
        return reviews;
    }
    public Company getCompany(Long companyId){
        Company company = null;
        try {
            company = companyClient.getCompany(companyId);
        } catch (FeignException.NotFound e) {
            // 404 Not Found - handle it by using an empty list
            company = null;
        }
        return company;
    }

//    @CircuitBreaker(name="companyBreaker",fallbackMethod = "companyBreakerFallback")
//    @Retry(name="companyBreaker",fallbackMethod = "companyBreakerFallback")
//    @RateLimiter(name="companyBreaker",fallbackMethod = "companyBreakerFallback")
    public List<JobWithCompanyDTO> findAll(){
        List<Job> list = repository.findAll();
        List<JobWithCompanyDTO> jobWithCompanyDTOS = new ArrayList<>();
        for(Job job:list){
            Company company = getCompany(job.getCompanyId());
            List<Review> reviewList = getReviewList(company);
            jobWithCompanyDTOS.add( new JobMapper().mapToJobWithCompanyDTO(job,company,reviewList));
        }
        return jobWithCompanyDTOS;
    }

    public List<String> companyBreakerFallback(Exception e){
        List<String> list = new ArrayList<>();
        list.add("Dummy");
        return list;
    }

    public String createJob(Job job){
        Job jobCreated = repository.save(job);
        if(jobCreated != null)
            return "JOB CREATED";
        else return "JOB CREATION FAILED";
    }

    public JobWithCompanyDTO findJob(Long jobId) {
        Job currJob = repository.findById(jobId).orElse(null);
        if(currJob == null) return null;
        Company company = companyClient.getCompany(currJob.getCompanyId());
        List<Review> reviewList = getReviewList(company);
        JobWithCompanyDTO jobWithCompanyDTO = new JobMapper().mapToJobWithCompanyDTO(currJob,company,reviewList);
        return jobWithCompanyDTO;
    }

    public void deleteJobById(Long jobId) {
        repository.deleteById(jobId);
    }

    public String updateJobById(Long jobId, Job job) {
        Job jobCreated = repository.findById(jobId).orElse(null);
        if(jobCreated != null) {
            repository.save(job);
            return "JOB UPDATED";
        }
        else return "JOB UPDATE FAILED";
    }
}
