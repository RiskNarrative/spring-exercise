package com.example.SpringExercise.Service;

import com.example.SpringExercise.CustomExceptions.CustomException;
import com.example.SpringExercise.Entity.CompanyEntity;
import com.example.SpringExercise.Model.CompanySearchRequest;
import com.example.SpringExercise.Model.CompanySearchResponse;
import com.example.SpringExercise.Repository.CompanyRepository;
import com.example.SpringExercise.Utility.TruProxyClient;
import com.example.SpringExercise.dto.CompanyDetails;
import com.example.SpringExercise.dto.Mapper.CompanyMapper;
import com.example.SpringExercise.dto.Mapper.OfficerMapper;
import com.example.SpringExercise.dto.Officer;
import com.example.SpringExercise.dto.truProxy.CompanyResponse;
import com.example.SpringExercise.dto.truProxy.CompanySearchResult;
import com.example.SpringExercise.dto.truProxy.OfficerSearchResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CompanyService {


    @Value("${truproxy.api.baseUrl}")
    private String baseUrl;
    @Autowired
    private TruProxyClient truProxyClient;

    @Autowired
    CompanyRepository companyRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * @param request
     * @param onlyActiveCompanies
     * @return
     */
    public CompanySearchResponse searchCompany(CompanySearchRequest request, boolean onlyActiveCompanies) {
        try {
            CompanySearchResponse response = new CompanySearchResponse();
            Optional<CompanyEntity> companyEntity = companyRepository.findById(request.getCompanyNumber());
            if (companyEntity.isPresent()) {
                response.setTotalResults(1);
                response.addItems(objectMapper.convertValue(companyEntity.get(), CompanyDetails.class));
                return response;
            }
            String searchParam = request.getCompanyNumber() != null ? request.getCompanyNumber() : request.getCompanyName();
            String queryUrl = String.format("%s/Companies/v1/Search?Query=%s", baseUrl, searchParam);

            ResponseEntity<CompanySearchResult> apiResponse = truProxyClient.getAPIResponseFromTruProxyClient(queryUrl, CompanySearchResult.class);
            List<CompanyResponse> companyResponseList = filterCompanies(onlyActiveCompanies, apiResponse);

            response.setTotalResults(companyResponseList.size());

            for (CompanyResponse companyResponse : companyResponseList) {
                // retrieve officers for each company
                List<Officer> officers = getOfficersFromTruProxyAPI(companyResponse.getCompany_number());
                CompanyDetails companyDetails = CompanyMapper.mapCompanyAPIResponseToCompany(companyResponse, officers);
                CompanyEntity company = objectMapper.convertValue(companyDetails, CompanyEntity.class);
                companyRepository.save(company);
                response.addItems(companyDetails);
            }
            return response;
        } catch (HttpClientErrorException ex) {
            log.error("HTTP Client Error: ", ex);
            throw ex;
        } catch (JsonProcessingException e) {
            log.error("JSON Processing Error: ", e);
            throw new CustomException("JSON_PROCESSING_ERROR", e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected Error: ", e);
            throw new CustomException("INTERNAL_SERVER_ERROR", "An unexpected error occurred");
        }
    }

    /**
     * @param onlyActiveCompanies
     * @param response
     * @return
     */
    public static List<CompanyResponse> filterCompanies(boolean onlyActiveCompanies, ResponseEntity<CompanySearchResult> response) {
        CompanySearchResult result = response.getBody();
        List<CompanyResponse> companies = result.getItems();

        if (companies == null || companies.size() == 0) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "No company found.");
        }

        return companies.stream()
                .filter(c -> !onlyActiveCompanies || "active".equalsIgnoreCase(c.getCompany_status()))
                .collect(Collectors.toList());
    }

    /**
     * @param companyNumber
     * @return
     * @throws JsonProcessingException
     */
    public List<Officer> getOfficersFromTruProxyAPI(String companyNumber) throws JsonProcessingException {
        String officerUrl = String.format("%s/Companies/v1/Officers?CompanyNumber=%s", baseUrl, companyNumber);

        ResponseEntity<OfficerSearchResult> officersResults = truProxyClient.getAPIResponseFromTruProxyClient(officerUrl, OfficerSearchResult.class);

        return officersResults.getBody()
                .getItems()
                .stream()
                .filter(o -> o.getResigned_on() == null)
                .map(OfficerMapper::mapOfficerAPIResponseToOfficer)
                .collect(Collectors.toList());
    }

}