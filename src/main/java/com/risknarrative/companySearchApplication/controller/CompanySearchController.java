package com.risknarrative.companySearchApplication.controller;

import com.risknarrative.companySearchApplication.request.CompanySearchRequest;
import com.risknarrative.companySearchApplication.response.CompanySearchResponse;
import com.risknarrative.companySearchApplication.service.CompanySearchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class CompanySearchController {

    private final CompanySearchService companySearchService;

   @PostMapping(path="/companyandofficerslookup", produces = "application/json")
    public ResponseEntity<CompanySearchResponse> performCompanyAndOfficersLookup(@RequestHeader String apiKey, @RequestParam(required = false) String companyStatus, @RequestBody @Valid CompanySearchRequest companySearchRequest) {
       CompanySearchResponse companySearchResponse = companySearchService.companyAndOfficersLookup(apiKey, companyStatus, companySearchRequest);
       return ResponseEntity.ok(companySearchResponse);
    }

}
