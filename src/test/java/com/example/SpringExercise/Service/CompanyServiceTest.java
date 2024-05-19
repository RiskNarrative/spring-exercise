package com.example.SpringExercise.Service;

import com.example.SpringExercise.Entity.CompanyEntity;
import com.example.SpringExercise.Model.CompanySearchRequest;
import com.example.SpringExercise.Model.CompanySearchResponse;
import com.example.SpringExercise.Repository.CompanyRepository;
import com.example.SpringExercise.Utility.TruProxyClient;
import com.example.SpringExercise.dto.truProxy.CompanyResponse;
import com.example.SpringExercise.dto.truProxy.CompanySearchResult;
import com.example.SpringExercise.dto.truProxy.OfficerResponse;
import com.example.SpringExercise.dto.truProxy.OfficerSearchResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

    @Mock
    private TruProxyClient truProxyClient;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testSearchCompany_WithExistingCompany() throws Exception {
        CompanySearchRequest request = new CompanySearchRequest();
        request.setCompanyNumber("06500244");

        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setCompanyNumber("06500244");

        when(companyRepository.findById("06500244")).thenReturn(Optional.of(companyEntity));

        CompanySearchResponse response = companyService.searchCompany(request, false);

        assertEquals(1, response.getTotalResults());
        verify(companyRepository, times(1)).findById("06500244");
        verify(truProxyClient, times(0)).getAPIResponseFromTruProxyClient(anyString(), eq(CompanySearchResult.class));
    }

    @Test
    public void testSearchCompany_WithNewCompany() throws Exception {
        CompanySearchRequest request = new CompanySearchRequest();
        request.setCompanyNumber("06500244");

        when(companyRepository.findById("06500244")).thenReturn(Optional.empty());

        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setCompany_number("06500244");
        companyResponse.setCompany_status("active");

        CompanySearchResult searchResult = new CompanySearchResult();
        searchResult.setItems(Collections.singletonList(companyResponse));

        ResponseEntity<CompanySearchResult> apiResponse = new ResponseEntity<>(searchResult, HttpStatus.OK);

        when(truProxyClient.getAPIResponseFromTruProxyClient(anyString(), eq(CompanySearchResult.class)))
                .thenReturn(apiResponse);

        OfficerResponse officer = new OfficerResponse();
        OfficerSearchResult officerSearchResult = new OfficerSearchResult();
        officerSearchResult.setItems(Collections.singletonList(officer));

        ResponseEntity<OfficerSearchResult> officerApiResponse = new ResponseEntity<>(officerSearchResult, HttpStatus.OK);

        when(truProxyClient.getAPIResponseFromTruProxyClient(anyString(), eq(OfficerSearchResult.class)))
                .thenReturn(officerApiResponse);

        CompanySearchResponse response = companyService.searchCompany(request, false);

        assertEquals(1, response.getTotalResults());
        verify(companyRepository, times(1)).findById("06500244");
        verify(truProxyClient, times(1)).getAPIResponseFromTruProxyClient(anyString(), eq(CompanySearchResult.class));
        verify(truProxyClient, times(1)).getAPIResponseFromTruProxyClient(anyString(), eq(OfficerSearchResult.class));
        verify(companyRepository, times(1)).save(any(CompanyEntity.class));
    }

    @Test
    public void testSearchCompany_WithHttpClientErrorException() throws JsonProcessingException {
        CompanySearchRequest request = new CompanySearchRequest();
        request.setCompanyNumber("06500244");

        when(companyRepository.findById("06500244")).thenReturn(Optional.empty());

        when(truProxyClient.getAPIResponseFromTruProxyClient(anyString(), eq(CompanySearchResult.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        assertThrows(HttpClientErrorException.class, () -> {
            companyService.searchCompany(request, false);
        });

        verify(companyRepository, times(1)).findById("06500244");
        verify(truProxyClient, times(1)).getAPIResponseFromTruProxyClient(anyString(), eq(CompanySearchResult.class));
    }


}
