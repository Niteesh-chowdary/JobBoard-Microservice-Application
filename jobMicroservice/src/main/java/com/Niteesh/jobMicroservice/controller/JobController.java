package com.Niteesh.jobMicroservice.controller;


import com.Niteesh.jobMicroservice.dto.JobWithCompanyDTO;
import com.Niteesh.jobMicroservice.model.Job;
import com.Niteesh.jobMicroservice.service.JobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private JobService jobService;

    public JobController(JobService jobService){
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<JobWithCompanyDTO>> findAll(){
        List<JobWithCompanyDTO> jobWithCompanyDTOS = jobService.findAll();
        if(jobWithCompanyDTOS != null)
            return new ResponseEntity<>(jobWithCompanyDTOS, HttpStatus.OK);
        else return new ResponseEntity<>(jobWithCompanyDTOS,HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> createJob(@RequestBody Job job){
        String jobPosted = jobService.createJob(job);
        if(jobPosted.equals("JOB CREATED"))
            return new ResponseEntity<>(jobPosted,HttpStatus.CREATED);
        else return new ResponseEntity<>(jobPosted,HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<JobWithCompanyDTO> findJob(@PathVariable Long jobId){
        JobWithCompanyDTO jobWithCompanyDTO = jobService.findJob(jobId);
        if(jobWithCompanyDTO != null)
            return new ResponseEntity<>(jobWithCompanyDTO, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity<String> deleteJobById(@PathVariable Long jobId){
        JobWithCompanyDTO job = jobService.findJob(jobId);
        if(job == null){
            return new ResponseEntity<>("NO ITEM",HttpStatus.NOT_FOUND);
        }
        else {
            jobService.deleteJobById(jobId);
            return new ResponseEntity<>("SUCCESSFULLY DELETED", HttpStatus.OK);
        }
    }

    @PutMapping("/{jobId}")
    public ResponseEntity<String> updateJobById(@PathVariable Long jobId, @RequestBody Job job){
        String jobUpdateStatus = jobService.updateJobById(jobId,job);
        if(jobUpdateStatus.equals("JOB UPDATED"))
            return new ResponseEntity<>(jobUpdateStatus,HttpStatus.OK);
        else return new ResponseEntity<>(jobUpdateStatus,HttpStatus.NOT_FOUND);
    }
}
