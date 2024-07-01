package lexisnexis.rn.springboot.controller;

import lexisnexis.rn.springboot.service.CompanyService;
import lexisnexis.rn.springboot.utils.CompanySearchRequest;
import lexisnexis.rn.springboot.utils.CompanySearchResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class ControllerTest {

    @Mock
    private CompanyService companyService;

    @InjectMocks
    private Controller controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findCompanyTest() throws Exception {
        // Mock the behavior of CompanyService
        String apiKey = "test-api-key";
        CompanySearchRequest request = new CompanySearchRequest();

        CompanySearchResponse response = new CompanySearchResponse();
        response.setTotalResults(1);

        when(companyService.findCompany(eq(apiKey), any(CompanySearchRequest.class))).thenReturn(response);
        ResponseEntity<CompanySearchResponse> responseEntity = controller.searchCompanies(apiKey, request);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

}
