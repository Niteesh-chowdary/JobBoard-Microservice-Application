package com.Niteesh.companyMicroservice.repository;


import com.Niteesh.companyMicroservice.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {
}
