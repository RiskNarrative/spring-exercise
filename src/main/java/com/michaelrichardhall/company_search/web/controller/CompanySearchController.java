package com.michaelrichardhall.company_search.web.controller;

import com.michaelrichardhall.company_search.service.CompanySearchService;
import com.michaelrichardhall.company_search.web.dto.Company;
import com.michaelrichardhall.company_search.web.dto.CompanySearchRequest;
import com.michaelrichardhall.company_search.web.dto.CompanySearchResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanySearchController {

    @Autowired
    CompanySearchService companySearchService;

    @GetMapping()
    public CompanySearchResponse getCompaniesByCompanyNameAndNumber(@Valid @RequestBody CompanySearchRequest request,
                                                                    @RequestParam(defaultValue = "false") Boolean activeOnly) {
        List<Company> companies = companySearchService.getCompanyByCompanyNameOrNumber(request.getCompanyName(), request.getCompanyNumber(), activeOnly == Boolean.TRUE);
        return CompanySearchResponse.builder().items(companies).total_results(companies.size()).build();
    }
}
