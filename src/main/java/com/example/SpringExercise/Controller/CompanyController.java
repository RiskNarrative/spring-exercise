package com.example.SpringExercise.Controller;


import com.example.SpringExercise.Model.CompanySearchRequest;
import com.example.SpringExercise.Model.CompanySearchResponse;
import com.example.SpringExercise.Service.CompanyService;
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
                                                               @RequestParam(defaultValue = "false") boolean onlyActive) {
        CompanySearchResponse response = companyService.searchCompany(request, onlyActive);
        return ResponseEntity.ok(response);
    }

}