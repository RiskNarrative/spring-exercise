package com.sachdeva.service;

import com.sachdeva.dto.CompanyDetails;
import com.sachdeva.dto.mapper.OfficerMapper;
import com.sachdeva.dto.truproxy.OfficerResponse;
import com.sachdeva.dto.truproxy.OfficerSearchResult;
import com.sachdeva.model.CompanySearchRequest;
import com.sachdeva.dto.Officer;
import com.sachdeva.dto.mapper.CompanyMapper;
import com.sachdeva.dto.truproxy.CompanyResponse;
import com.sachdeva.dto.truproxy.CompanySearchResult;
import com.sachdeva.model.CompanySearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    @Value("${truproxy.api.key}")
    private String apiKey;
    @Value("${truproxy.api.baseUrl}")
    private String baseUrl;
    private final RestTemplate restTemplate;

    public CompanyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CompanySearchResponse searchCompany(CompanySearchRequest request, boolean onlyActiveCompanies){
        try{
            //Get companies

            ResponseEntity<CompanySearchResult> apiResponse = getAPIResponseFromTruProxyService( request);
            List<CompanyResponse> companyResponseList = extractCompaniesWithFilters(onlyActiveCompanies, apiResponse);

            CompanySearchResponse response = new CompanySearchResponse();
            response.setTotalResults(companyResponseList.size());

            for(CompanyResponse companyResponse : companyResponseList) {
                // Get the officers for the company
                List<Officer> officers = getOfficersFromTruProxyAPI(companyResponse.getCompany_number());
                CompanyDetails companyDetails = CompanyMapper.mapCompanyAPIResponseToCompany(companyResponse, officers);
                response.addItems(companyDetails);
            }

        return response;

    } catch (HttpClientErrorException ex) {
        // Handle specific HTTP errors as needed
        throw ex;
    }
}

    private ResponseEntity<CompanySearchResult> getAPIResponseFromTruProxyService(CompanySearchRequest request){
        //Building the API url to hit
        String searchParam = request.getCompanyNumber() != null ? request.getCompanyNumber() : request.getCompanyName();
        String queryUrl = String.format("%s/Companies/v1/Search?Query=%s",baseUrl,searchParam);

        // setting up http headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key",apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        System.out.println("Hitting company service with url: "+queryUrl+" and headers: {}"+ headers);
        return restTemplate.exchange(
                queryUrl,
                HttpMethod.GET,
                entity,
                CompanySearchResult.class
        );
    }

    private static List<CompanyResponse> extractCompaniesWithFilters(boolean onlyActiveCompanies, ResponseEntity<CompanySearchResult> response) {
        CompanySearchResult result = response.getBody();
        List<CompanyResponse> companies = result.getItems();

        if (companies == null || companies.size() == 0) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "No company found.");
        }

        return companies.stream()
                .filter(c -> !onlyActiveCompanies || "active".equalsIgnoreCase(c.getCompany_status()))
                .collect(Collectors.toList());
    }

    private List<Officer> getOfficersFromTruProxyAPI(String companyNumber) {
        String officerUrl = String.format("%s/Companies/v1/Officers?CompanyNumber=%s", baseUrl, companyNumber);
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key",apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        System.out.println("Hitting officer service with url: "+officerUrl+" and headers: {}"+ headers);
        ResponseEntity<OfficerSearchResult> officersResults = restTemplate.exchange(
                officerUrl,
                HttpMethod.GET,
                entity,
                OfficerSearchResult.class
        );
        return officersResults.getBody()
                .getItems()
                .stream()
                .filter(o -> o.getResigned_on() == null)
                .map(OfficerMapper::mapOfficerAPIResponseToOfficer)
                .collect(Collectors.toList());
}



}
