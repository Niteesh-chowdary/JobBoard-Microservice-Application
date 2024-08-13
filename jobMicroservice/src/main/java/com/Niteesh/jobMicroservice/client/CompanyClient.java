package com.Niteesh.jobMicroservice.client;

import com.Niteesh.jobMicroservice.external.Company;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="COMPANYMICROSERVICE",url = "${company-service.url}")
public interface CompanyClient {
    @GetMapping("/companies/{companyId}")
    public Company getCompany(@PathVariable Long companyId);
}
