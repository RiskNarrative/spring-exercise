
package com.sachdeva.controller;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.sachdeva.model.CompanySearchRequest;
import com.sachdeva.model.CompanySearchResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompanyControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    static WireMockServer wireMockServer;

    @BeforeAll
    static void setupWireMock() {
        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();
    }

    @BeforeEach
    void setup() {
        wireMockServer.resetAll();
    }

    @Test
    void searchCompanyByCompanyNumber() {
        // Stub WireMock for company search
        wireMockServer.stubFor(get(urlPathMatching("/TruProxyAPI/rest/Companies/v1/Search"))
                .withQueryParam("Query", equalTo("06500244"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "  \"total_results\": 1,\n" +
                                "  \"items\": [\n" +
                                "    {\n" +
                                "      \"company_number\": \"06500244\",\n" +
                                "      \"company_type\": \"ltd\",\n" +
                                "      \"title\": \"BBC LIMITED\",\n" +
                                "      \"company_status\": \"active\",\n" +
                                "      \"date_of_creation\": \"2008-02-11\",\n" +
                                "      \"address\": {\n" +
                                "        \"locality\": \"Retford\",\n" +
                                "        \"postal_code\": \"DN22 0AD\",\n" +
                                "        \"premises\": \"Boswell Cottage Main Street\",\n" +
                                "        \"address_line_1\": \"North Leverton\",\n" +
                                "        \"country\": \"England\"\n" +
                                "      },\n" +
                                "      \"officers\": [\n" +
                                "        {\n" +
                                "          \"name\": \"BOXALL, Sarah Victoria\",\n" +
                                "          \"officer_role\": \"secretary\",\n" +
                                "          \"appointed_on\": \"2008-02-11\",\n" +
                                "          \"address\": {\n" +
                                "            \"premises\": \"5\",\n" +
                                "            \"locality\": \"London\",\n" +
                                "            \"address_line_1\": \"Cranford Close\",\n" +
                                "            \"country\": \"England\",\n" +
                                "            \"postal_code\": \"SW20 0DP\"\n" +
                                "          }\n" +
                                "        }\n" +
                                "      ]\n" +
                                "    }\n" +
                                "  ]\n" +
                                "}")));

        // Company search request
        CompanySearchRequest request = new CompanySearchRequest();
        request.setCompanyNumber("06500244");
        ResponseEntity<CompanySearchResponse> response = restTemplate.postForEntity("/api/companies/search?onlyActive=true", request, CompanySearchResponse.class);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().getItems().get(0).getCompany_number().equals("06500244"));
        assertTrue(response.getBody().getItems().get(0).getOfficers().get(0).getName().equalsIgnoreCase("BOXALL, Sarah Victoria"));
    }

    @AfterAll
    static void tearDown() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }


}

