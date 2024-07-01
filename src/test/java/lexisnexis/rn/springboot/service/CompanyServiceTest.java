package lexisnexis.rn.springboot.service;

import lexisnexis.rn.springboot.Entity.AddressEntity;
import lexisnexis.rn.springboot.Entity.CompanyEntity;
import lexisnexis.rn.springboot.Entity.OfficerEntity;
import lexisnexis.rn.springboot.repository.CompanyRepository;
import lexisnexis.rn.springboot.utils.CompanySearchRequest;
import lexisnexis.rn.springboot.utils.CompanySearchResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    @Mock
    private TruProxyServiceInvoker truProxyServiceInvoker;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;

    private CompanySearchRequest request;

    @BeforeEach
    void setup() {
        request = new CompanySearchRequest();
        request.setCompanyNumber("12345");
    }

    @Test
    void testFindCompany_CompanyFoundInDatabase() throws Exception {
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setCompanyNumber("12345");
        companyEntity.setOfficers(Collections.singletonList(OfficerEntity.builder().address(getDummyAddress()).build()));
        companyEntity.setAddress(getDummyAddress());
        when(companyRepository.findById("12345")).thenReturn(Optional.of(companyEntity));

        CompanySearchResponse response = companyService.findCompany("apiKey", request);

        assertEquals(1, response.getTotalResults());
        assertEquals("12345", response.getItems().get(0).getCompanyNumber());
    }

    @Test
    void testFindCompany_CompanyNotFoundInDatabaseOrApi() {
        when(companyRepository.findById("12345")).thenReturn(Optional.empty());

        when(truProxyServiceInvoker.searchCompanies(any(), any()))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND, "No company found."));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            companyService.findCompany("apiKey", request);
        });

        assertEquals("Error fetching data from TruProxyAPI: 404 NOT_FOUND", exception.getMessage());
    }

    @Test
    void testFindCompany_InvalidInput() {
        request.setCompanyNumber(null);

        Exception exception = assertThrows(Exception.class, () -> {
            companyService.findCompany("apiKey", request);
        });

        assertEquals("Invalid input ", exception.getMessage());
    }

    private AddressEntity getDummyAddress (){
        return AddressEntity.builder().locality("some locality").addressLine1("some addressLine1").country("UK").build();
    }
}