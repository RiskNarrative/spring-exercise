package com.michaelrichardhall.company_search.tru_proxy_api.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.michaelrichardhall.company_search.configuration.RestClientConfiguration;
import com.michaelrichardhall.company_search.tru_proxy_api.responses.TruProxyAPICompany;
import com.michaelrichardhall.company_search.tru_proxy_api.responses.TruProxyAPIOfficer;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {Gateway.class, RestClientConfiguration.class})
@TestPropertySource(properties = {"TruProxyAPI.baseUrl=http://localhost:9000", "TruProxyAPI.apiToken=XXXXXXXX"})
class GatewayTest {

    public static final String CONTENT_TYPE_APPLICATION_JSON = "Content-Type: application/json";
    private static String TRU_PROXY_COMPANY_SEARCH_RESPONSE = "";
    private static String TRU_PROXY_API_COMPANY_OFFICERS_SEARCH_RESPONSE = "";
    private static final String COMPANY_NAME = "CompanyName";
    private static final String COMPANY_NUMBER = "0123456";

    @Autowired
    private Gateway gateway;

    private static MockWebServer mockWebServer;

    private static final ObjectMapper mapper = new ObjectMapper();

    @BeforeAll
    public void setUp() throws IOException {

        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("TruProxyAPICompanySearchResponse.json")) {
            TRU_PROXY_COMPANY_SEARCH_RESPONSE = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("TruProxyAPICompanyOfficersSearchResponse.json")) {
            TRU_PROXY_API_COMPANY_OFFICERS_SEARCH_RESPONSE = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }


        mockWebServer = new MockWebServer();
        mockWebServer.start(9000);

    }

    @AfterAll
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void searchCompaniesByName() {
        // Arrange
        mockWebServer.enqueue(new MockResponse().addHeader(CONTENT_TYPE_APPLICATION_JSON).setBody(TRU_PROXY_COMPANY_SEARCH_RESPONSE));

        // Act
        List<TruProxyAPICompany> companyList = gateway.searchCompanies(COMPANY_NAME, null);

        // Assert
        assertEquals(2, companyList.size());

    }

    @Test
    void searchCompaniesByNameAndNumber() {
        // Arrange
        mockWebServer.enqueue(new MockResponse().addHeader(CONTENT_TYPE_APPLICATION_JSON).setBody(TRU_PROXY_COMPANY_SEARCH_RESPONSE));

        // Act
        List<TruProxyAPICompany> companyList = gateway.searchCompanies(COMPANY_NAME, COMPANY_NUMBER);

        // Assert
        assertEquals(2, companyList.size());

    }

    @Test
    void getCompanyOfficersByCompanyNumber() {
        // Arrange
        mockWebServer.enqueue(new MockResponse().addHeader(CONTENT_TYPE_APPLICATION_JSON).setBody(TRU_PROXY_API_COMPANY_OFFICERS_SEARCH_RESPONSE));

        // Act
        List<TruProxyAPIOfficer> officerList = gateway.getCompanyOfficersByCompanyNumber(COMPANY_NUMBER);

        // Assert
        assertEquals(2, officerList.size());

    }
}
