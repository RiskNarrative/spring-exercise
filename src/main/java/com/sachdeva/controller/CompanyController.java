package com.sachdeva.controller;

import com.sachdeva.dto.CompanyDetails;
import com.sachdeva.model.CompanySearchRequest;
import com.sachdeva.model.CompanySearchResponse;
import com.sachdeva.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/search")
    public ResponseEntity<CompanySearchResponse> searchCompany(@RequestBody CompanySearchRequest request,
                                                        @RequestParam(defaultValue = "true") boolean onlyActive) {
        CompanySearchResponse response = companyService.searchCompany(request, onlyActive);
        return ResponseEntity.ok(response);
    }

}
