package com.Niteesh.jobMicroservice.repository;


import com.Niteesh.jobMicroservice.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job,Long> {
}
