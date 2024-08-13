package com.Niteesh.companyMicroservice.controller;

import com.Niteesh.companyMicroservice.dto.CompanyWithReviewDTO;
import com.Niteesh.companyMicroservice.model.Company;
import com.Niteesh.companyMicroservice.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService service){
        this.companyService = service;
    }

    @GetMapping
    public ResponseEntity<List<CompanyWithReviewDTO>> findAll(){
        List<CompanyWithReviewDTO> companyWithReviewDTOS = companyService.findAll();
        if(companyWithReviewDTOS != null){
            return new ResponseEntity<>(companyWithReviewDTOS,HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> createCompany(@RequestBody Company company){
        String companyPosted = companyService.createCompany(company);
        if(companyPosted.equals("COMPANY CREATED"))
            return new ResponseEntity<>(companyPosted,HttpStatus.CREATED);
        else return new ResponseEntity<>(companyPosted,HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyWithReviewDTO> findCompanyById(@PathVariable Long companyId){
        CompanyWithReviewDTO companyWithReviewDTO = companyService.findCompanyById(companyId);
        if(companyWithReviewDTO != null){
            return new ResponseEntity<>(companyWithReviewDTO,HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{companyId}")
    public ResponseEntity<String> deleteCompanyById(@PathVariable Long companyId){
        CompanyWithReviewDTO companyWithReviewDTO = companyService.findCompanyById(companyId);
        if(companyWithReviewDTO == null){
            return new ResponseEntity<>("NO ITEM",HttpStatus.NOT_FOUND);
        }
        else{
            companyService.deleteCompanyById(companyId);
            return new ResponseEntity<>("SUCCESSFULLY DELETED", HttpStatus.OK);
        }
    }

    @PutMapping("/{companyId}")
    public ResponseEntity<String> updateCompanyById(@PathVariable Long companyId, @RequestBody Company company){
        String companyUpdateStatus = companyService.updateCompanyById(companyId,company);
        if(companyUpdateStatus.equals("JOB UPDATED"))
            return new ResponseEntity<>(companyUpdateStatus,HttpStatus.OK);
        else return new ResponseEntity<>(companyUpdateStatus,HttpStatus.NOT_FOUND);
    }
}
