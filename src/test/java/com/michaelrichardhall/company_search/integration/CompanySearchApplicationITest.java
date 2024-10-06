package com.michaelrichardhall.company_search.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.michaelrichardhall.company_search.web.dto.CompanySearchRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest
@WireMockTest(httpPort = 9000)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
class CompanySearchApplicationITest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private static String COMPANY_SEARCH_RESPONSE_EMPTY = "";
    private static String COMPANY_SEARCH_RESPONSE_FILTERED = "";
    private static String COMPANY_SEARCH_RESPONSE_ALL = "";
    private static String TRU_PROXY_API_COMPANY_SEARCH_RESPONSE = "";
    private static String TRU_PROXY_API_COMPANY_SEARCH_RESPONSE_EMPTY = "";
    private static String TRU_PROXY_API_COMPANY_OFFICERS_SEARCH_RESPONSE = "";

    @BeforeAll
    public void setUp() throws IOException {

        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("TruProxyAPICompanySearchResponse.json")) {
            TRU_PROXY_API_COMPANY_SEARCH_RESPONSE = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("TruProxyAPICompanySearchEmptyResponse.json")) {
            TRU_PROXY_API_COMPANY_SEARCH_RESPONSE_EMPTY = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }

        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("TruProxyAPICompanyOfficersSearchResponse.json")) {
            TRU_PROXY_API_COMPANY_OFFICERS_SEARCH_RESPONSE = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }

        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("CompanySearchResponse.json")) {
            COMPANY_SEARCH_RESPONSE_FILTERED = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }

        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("CompanySearchResponseAll.json")) {
            COMPANY_SEARCH_RESPONSE_ALL = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }

        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("CompanySearchResponseEmpty.json")) {
            COMPANY_SEARCH_RESPONSE_EMPTY = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }


    }

    @Test
    void givenCompanyName_whenGetCompany_thenReturnOK(WireMockRuntimeInfo wireMockRuntimeInfo) throws Exception {
        // Arrange
        stubFor(get(urlPathEqualTo("/TruProxyAPI/rest/Companies/v1/Search"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(TRU_PROXY_API_COMPANY_SEARCH_RESPONSE)
                        .withStatus(200)));
        stubFor(get(urlPathEqualTo("/TruProxyAPI/rest/Companies/v1/Officers"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(TRU_PROXY_API_COMPANY_OFFICERS_SEARCH_RESPONSE).withStatus(200)));

        // Act
        mockMvc.perform(MockMvcRequestBuilders.get("/company")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(aCompanySearchRequest())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(COMPANY_SEARCH_RESPONSE_ALL));
        // Assert
    }

    @Test
    void givenCompanyName_whenGetCompany_andActiveOnly_thenReturnOK(WireMockRuntimeInfo wireMockRuntimeInfo) throws Exception {
        // Arrange
        stubFor(get(urlPathEqualTo("/TruProxyAPI/rest/Companies/v1/Search"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(TRU_PROXY_API_COMPANY_SEARCH_RESPONSE)
                        .withStatus(200)));
        stubFor(get(urlPathEqualTo("/TruProxyAPI/rest/Companies/v1/Officers"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(TRU_PROXY_API_COMPANY_OFFICERS_SEARCH_RESPONSE).withStatus(200)));

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/company?activeOnly=true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(aCompanySearchRequest())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(COMPANY_SEARCH_RESPONSE_FILTERED));
    }

    @Test
    void givenInvalidCompanySearchRequest_whenGetCompany_thenBadRequest() throws Exception {
        // Arrange
        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/company")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(anInvalidCompanySearchRequest())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void givenCompanySearchRequest_andNoResults_thenEmptyListReturned() throws Exception {
        // Arrange
        stubFor(get(urlPathEqualTo("/TruProxyAPI/rest/Companies/v1/Search"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(TRU_PROXY_API_COMPANY_SEARCH_RESPONSE_EMPTY)
                        .withStatus(200)));

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/company?activeOnly=true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(aCompanySearchRequest())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(COMPANY_SEARCH_RESPONSE_EMPTY));
    }

    private CompanySearchRequest aCompanySearchRequest() {
        return CompanySearchRequest.builder()
                .companyName("Company Name")
                .build();
    }

    private CompanySearchRequest anInvalidCompanySearchRequest() {
        return CompanySearchRequest.builder()
                .companyName(null)
                .build();
    }

}
