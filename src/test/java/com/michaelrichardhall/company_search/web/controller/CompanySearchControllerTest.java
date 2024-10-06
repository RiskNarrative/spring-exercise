package com.michaelrichardhall.company_search.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.michaelrichardhall.company_search.service.CompanySearchService;
import com.michaelrichardhall.company_search.web.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(CompanySearchController.class)
class CompanySearchControllerTest {

    public static final String COMPANY_NUMBER = "0123456";
    public static final String COMPANY_NAME = "Company Name";
    @Autowired
    MockMvc mockMvc;

    @MockBean
    CompanySearchService companySearchService;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    void givenCompanySearchRequestWithName_whenCompanySearch_thenReturnCompanySearchResponse() throws Exception {
        // Arrange
        CompanySearchRequest request = aCompanySearchRequest();
        when(companySearchService.getCompanyByCompanyNameOrNumber(anyString(), isNull(), anyBoolean())).thenReturn(aCompanyList());

        // Act
        MvcResult result = mockMvc.perform(get("/company")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        CompanySearchResponse companySearchResponse = mapper.readValue(result.getResponse().getContentAsString(), CompanySearchResponse.class);

        // Assert
        assertEquals(1, companySearchResponse.getTotal_results());
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(aCompanyList(), companySearchResponse.getItems());
    }

    @Test
    void givenCompanySearchRequestWithNameAndNumber_whenCompanySearch_thenReturnCompanySearchResponse() throws Exception {
        // Arrange
        CompanySearchRequest request = aCompanySearchRequestWithNumber();
        when(companySearchService.getCompanyByCompanyNameOrNumber(anyString(), anyString(), anyBoolean())).thenReturn(aCompanyList());

        // Act
        MvcResult result = mockMvc.perform(get("/company")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        CompanySearchResponse companySearchResponse = mapper.readValue(result.getResponse().getContentAsString(), CompanySearchResponse.class);

        // Assert
        assertEquals(1, companySearchResponse.getTotal_results());
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(aCompanyList(), companySearchResponse.getItems());
    }

    @Test
    void givenEmptyCompanySearchRequest_whenCompanySearch_thenReturn400() throws Exception {
        // Arrange
        CompanySearchRequest companySearchRequest = anInvalidCompanySearchRequest();

        // Act
        MvcResult result = mockMvc.perform(get("/company")
                .content(mapper.writeValueAsString(companySearchRequest))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());

    }

    private CompanySearchRequest aCompanySearchRequest() {
        return CompanySearchRequest.builder().companyName(COMPANY_NAME).build();
    }

    private CompanySearchRequest aCompanySearchRequestWithNumber() {
        return CompanySearchRequest.builder().companyName(COMPANY_NAME).companyNumber(COMPANY_NUMBER).build();
    }

    private CompanySearchRequest anInvalidCompanySearchRequest() {
        return CompanySearchRequest.builder().companyName(null).companyNumber(COMPANY_NUMBER).build();
    }

    private List<Company> aCompanyList() {
        return List.of(Company.builder().build());
    }

    private Company aCompany() {
        return Company.builder()
                .address(anAddress())
                .officers(List.of(anOfficer()))
                .build();
    }

    private Address anAddress() {
        return Address.builder().build();
    }

    private Officer anOfficer() {
        return Officer.builder()
                .address(anAddress())
                .build();
    }

}