package com.risknarrative.companySearchApplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.risknarrative.companySearchApplication.Constants;
import com.risknarrative.companySearchApplication.TestData;
import com.risknarrative.companySearchApplication.response.CompanySearchResponse;
import com.risknarrative.companySearchApplication.service.CompanySearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.risknarrative.companySearchApplication.TestData.API_KEY_VALUE;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CompanySearchController.class)
public class CompanySearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CompanySearchService companySearchServiceMockBean;

    @Test
    public void performCompanyAndOfficersLookupTest_Success() throws Exception {

        CompanySearchResponse companySearchResponse = TestData.conStructCompanySearchResponse();

        when(companySearchServiceMockBean.companyAndOfficersLookup(API_KEY_VALUE, Constants.ACTIVE, TestData.constructCompanySearchRequest())).thenReturn(companySearchResponse);

        mockMvc.perform(post("/companyandofficerslookup")
                        .header("apiKey",API_KEY_VALUE)
                        .param(Constants.COMAPNY_STATUS_PARAM,Constants.ACTIVE)
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(TestData.constructCompanySearchRequest())))
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString();

    }

    @Test
    public void performCompanyAndOfficersLookupTest_Error404() throws Exception {

        CompanySearchResponse companySearchResponse = TestData.conStructCompanySearchResponse();

        when(companySearchServiceMockBean.companyAndOfficersLookup(API_KEY_VALUE, Constants.ACTIVE, TestData.constructCompanySearchRequest())).thenReturn(companySearchResponse);

        mockMvc.perform(post("/companyandofficerslookup/wrongPath")
                        .header("apiKey",API_KEY_VALUE)
                        .param(Constants.COMAPNY_STATUS_PARAM,Constants.ACTIVE)
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(TestData.constructCompanySearchRequest())))
                .andExpect(status().isNotFound());
    }

    @Test
    public void performCompanyAndOfficersLookupTest_Error400() throws Exception {

        CompanySearchResponse companySearchResponse = TestData.conStructCompanySearchResponse();

        when(companySearchServiceMockBean.companyAndOfficersLookup(API_KEY_VALUE, Constants.ACTIVE, TestData.constructCompanySearchRequest())).thenReturn(companySearchResponse);

        mockMvc.perform(post("/companyandofficerslookup")
                        .header("apiKey",API_KEY_VALUE)
                        .param(Constants.COMAPNY_STATUS_PARAM,Constants.ACTIVE)
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString("wrongPayload")))
                .andExpect(status().isBadRequest());
    }


}