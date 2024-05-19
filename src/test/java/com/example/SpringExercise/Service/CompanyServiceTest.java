package com.example.SpringExercise.Service;

import com.example.SpringExercise.CustomExceptions.CustomException;
import com.example.SpringExercise.Model.CompanySearchRequest;
import com.example.SpringExercise.Model.CompanySearchResponse;
import com.example.SpringExercise.Utility.TruProxyClient;
import com.example.SpringExercise.dto.CompanyDetails;
import com.example.SpringExercise.dto.Mapper.CompanyMapper;
import com.example.SpringExercise.dto.Mapper.OfficerMapper;
import com.example.SpringExercise.dto.Officer;
import com.example.SpringExercise.dto.truProxy.CompanyResponse;
import com.example.SpringExercise.dto.truProxy.CompanySearchResult;
import com.example.SpringExercise.dto.truProxy.OfficerResponse;
import com.example.SpringExercise.dto.truProxy.OfficerSearchResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CompanyServiceTest {

    @Mock
    private TruProxyClient truProxyClient;

    @InjectMocks
    private CompanyService companyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearchCompanySuccess() throws JsonProcessingException {
        // Prepare mock data
        CompanySearchRequest request = new CompanySearchRequest();
        request.setCompanyName("Test Company");

        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setCompany_number("12345");
        companyResponse.setCompany_status("active");

        CompanySearchResult companySearchResult = new CompanySearchResult();
        companySearchResult.setItems(Collections.singletonList(companyResponse));

        OfficerResponse officer = new OfficerResponse();
        officer.setName("Test Officer");

        OfficerSearchResult officerSearchResult = new OfficerSearchResult();
        officerSearchResult.setItems(Collections.singletonList(officer));

        when(truProxyClient.getAPIResponseFromTruProxyClient(anyString(), eq(CompanySearchResult.class)))
                .thenReturn(new ResponseEntity<>(companySearchResult, HttpStatus.OK));
        when(truProxyClient.getAPIResponseFromTruProxyClient(anyString(), eq(OfficerSearchResult.class)))
                .thenReturn(new ResponseEntity<>(officerSearchResult, HttpStatus.OK));

        // Execute the service method
        CompanySearchResponse response = companyService.searchCompany(request, true);

        // Verify results
        assertNotNull(response);
        assertEquals(1, response.getTotalResults());
        List<CompanyDetails> companies = response.getItems();
        assertEquals(1, companies.size());
        assertEquals("12345", companies.get(0).getCompanyNumber());
        assertEquals(1, companies.get(0).getOfficers().size());
        assertEquals("Test Officer", companies.get(0).getOfficers().get(0).getName());
    }

    @Test
    void testSearchCompanyNoResults() throws JsonProcessingException {
        CompanySearchRequest request = new CompanySearchRequest();
        request.setCompanyName("Nonexistent Company");

        CompanySearchResult companySearchResult = new CompanySearchResult();
        companySearchResult.setItems(Collections.emptyList());

        when(truProxyClient.getAPIResponseFromTruProxyClient(anyString(), eq(CompanySearchResult.class)))
                .thenReturn(new ResponseEntity<>(companySearchResult, HttpStatus.OK));

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
            companyService.searchCompany(request, true);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void testSearchCompanyJsonProcessingException() throws JsonProcessingException {
        CompanySearchRequest request = new CompanySearchRequest();
        request.setCompanyName("Test Company");

        when(truProxyClient.getAPIResponseFromTruProxyClient(anyString(), eq(CompanySearchResult.class)))
                .thenThrow(new JsonProcessingException("Test JSON error") {});

        CustomException exception = assertThrows(CustomException.class, () -> {
            companyService.searchCompany(request, true);
        });

        assertEquals("JSON_PROCESSING_ERROR", exception.getErrorCode());
    }

    @Test
    void testSearchCompanyUnexpectedException() throws JsonProcessingException {
        CompanySearchRequest request = new CompanySearchRequest();
        request.setCompanyName("Test Company");

        when(truProxyClient.getAPIResponseFromTruProxyClient(anyString(), eq(CompanySearchResult.class)))
                .thenThrow(new RuntimeException("Test runtime error"));

        CustomException exception = assertThrows(CustomException.class, () -> {
            companyService.searchCompany(request, true);
        });

        assertEquals("INTERNAL_SERVER_ERROR", exception.getErrorCode());
    }
}
