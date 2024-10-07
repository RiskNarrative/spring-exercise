package com.risknarrative.companySearchApplication.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.risknarrative.companySearchApplication.TestData;
import com.risknarrative.companySearchApplication.response.CompanySearchResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import static com.risknarrative.companySearchApplication.TestData.WIREMOCK_PORT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompanySearchIntegrationTest {

        @Autowired
        private WebClient.Builder webClientBuilder;
        String apiKeyValue = "apiKeyValueTobeReplacedWithActualApiKey";

        @Test
        public void  givenWebClientBaseURLConfiguredToWireMock_whenGetRequestForCompanySearch_thenWebClientRecievesSuccessResponse() throws Exception {

            String requestBody = new ObjectMapper().writeValueAsString(TestData.constructCompanySearchRequest());

            WebClient webClient = webClientBuilder.clientConnector(connector()).baseUrl("http://localhost:" + WIREMOCK_PORT).build();

            CompanySearchResponse companySearchResponse = webClient.post()
                    .uri("/companyandofficerslookup")
                    .header("apiKey", apiKeyValue)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(CompanySearchResponse.class)
                    .block();
            assertNotNull(companySearchResponse);
            assertEquals("active", companySearchResponse.getItems().get(0).getCompanyStatus());
            assertEquals("director", companySearchResponse.getItems().get(0).getOfficers().get(0).getOfficerRole());
            assertEquals("ltd",  companySearchResponse.getItems().get(0).getCompanyType());
        }

    private ClientHttpConnector connector() {
        return new ReactorClientHttpConnector(HttpClient.create(ConnectionProvider.newConnection()));
    }
}
