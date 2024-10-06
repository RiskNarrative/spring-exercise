package com.michaelrichardhall.company_search.service;

import com.michaelrichardhall.company_search.tru_proxy_api.gateway.Gateway;
import com.michaelrichardhall.company_search.tru_proxy_api.responses.TruProxyAPIAddress;
import com.michaelrichardhall.company_search.tru_proxy_api.responses.TruProxyAPICompany;
import com.michaelrichardhall.company_search.tru_proxy_api.responses.TruProxyAPIOfficer;
import com.michaelrichardhall.company_search.web.dto.Address;
import com.michaelrichardhall.company_search.web.dto.Company;
import com.michaelrichardhall.company_search.web.dto.Officer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CompanySearchService.class})
class CompanySearchServiceTest {

    public static final String COMPANY_NAME = "Company Name";
    public static final String COMPANY_NUMBER = "0123456";

    @Autowired
    private CompanySearchService companySearchService;

    @MockBean
    private Gateway truProxyAPIGateway;

    @Test
    void givenCompanyNameAndNumber_whenSearchByCompanyName_thenListOfCompaniesReturned() {
        // Arrange
        when(truProxyAPIGateway.searchCompanies(anyString(), isNull())).thenReturn(aListOfTruProxyAPICompanies());
        when(truProxyAPIGateway.getCompanyOfficersByCompanyNumber(anyString())).thenReturn(aListOfTruProxyAPIOfficers());

        // Act
        List<Company> companies = companySearchService.getCompanyByCompanyNameOrNumber(COMPANY_NAME, null, true);

        // Assert
        assertEquals(1, companies.size());
        assertEquals(aCompany(), companies.getFirst());
        assertEquals(1, companies.getFirst().getOfficers().size());
    }

    @Test
    void givenCompanyNameAndNumber_whenSearchByCompanyNameIncludingInactive_thenListOfCompaniesReturned() {
        // Arrange
        when(truProxyAPIGateway.searchCompanies(anyString(), isNull())).thenReturn(aListOfTruProxyAPICompanies());
        when(truProxyAPIGateway.getCompanyOfficersByCompanyNumber(anyString())).thenReturn(aListOfTruProxyAPIOfficers());

        // Act
        List<Company> companies = companySearchService.getCompanyByCompanyNameOrNumber(COMPANY_NAME, null, false);

        // Assert
        assertEquals(2, companies.size());
        assertEquals(aCompany(), companies.getFirst());
        assertEquals(1, companies.getFirst().getOfficers().size());
    }

    @Test
    void givenCompanyNameAndNumber_whenSearchByCompanyNameAndNumber_thenListOfCompaniesReturned() {
        // Arrange
        when(truProxyAPIGateway.searchCompanies(anyString(), anyString())).thenReturn(aListOfTruProxyAPICompanies());
        when(truProxyAPIGateway.getCompanyOfficersByCompanyNumber(anyString())).thenReturn(aListOfTruProxyAPIOfficers());

        // Act
        List<Company> companies = companySearchService.getCompanyByCompanyNameOrNumber(COMPANY_NAME, COMPANY_NUMBER, true);

        // Assert
        assertEquals(1, companies.size());
        assertEquals(aCompany(), companies.getFirst());
        assertEquals(1, companies.getFirst().getOfficers().size());
    }

    @Test
    void givenCompanyNameAndNumber_whenSearchByCompanyNameAndNumberAndNoResults_thenEmptyListReturned() {
        // Arrange
        // Act
        List<Company> companies = companySearchService.getCompanyByCompanyNameOrNumber(COMPANY_NAME, COMPANY_NUMBER, true);

        // Assert
        assertEquals(0, companies.size());
    }

    private Company aCompany() {
        return Company.builder()
                .company_status("active")
                .company_number(COMPANY_NUMBER)
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

    private TruProxyAPICompany aTruProxyAPICompany() {
        return TruProxyAPICompany.builder()
                .company_status("active")
                .company_number(COMPANY_NUMBER)
                .address(aTruProxyAPIAddress())
                .build();
    }

    private TruProxyAPICompany anInactiveTruProxyAPICompany() {
        return TruProxyAPICompany.builder()
                .company_status("dissolved")
                .company_number(COMPANY_NUMBER)
                .address(aTruProxyAPIAddress())
                .build();
    }

    private List<TruProxyAPICompany> aListOfTruProxyAPICompanies() {
        return List.of(aTruProxyAPICompany(), anInactiveTruProxyAPICompany());
    }

    private TruProxyAPIAddress aTruProxyAPIAddress() {
        return TruProxyAPIAddress.builder().build();
    }

    private List<TruProxyAPIOfficer> aListOfTruProxyAPIOfficers() {
        return List.of(aTruProxyAPIOfficer(), aResignedTruProxyAPIOfficer());
    }

    private TruProxyAPIOfficer aTruProxyAPIOfficer() {
        return TruProxyAPIOfficer.builder()
                .address(aTruProxyAPIAddress())
                .build();
    }

    private TruProxyAPIOfficer aResignedTruProxyAPIOfficer() {
        return TruProxyAPIOfficer.builder()
                .address(aTruProxyAPIAddress())
                .resigned_on("2024-10-06")
                .build();
    }
}