package com.risknarrative.companySearchApplication.service;

import com.risknarrative.companySearchApplication.Constants;
import com.risknarrative.companySearchApplication.TestData;
import com.risknarrative.companySearchApplication.client.TruProxyApiClient;
import com.risknarrative.companySearchApplication.dto.companies.Item;
import com.risknarrative.companySearchApplication.mapper.CompanySearchResponseMapper;
import com.risknarrative.companySearchApplication.response.CompanySearchResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.risknarrative.companySearchApplication.TestData.COMPANY_NUMBER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompanySearchServiceImplTest {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private TruProxyApiClient truProxyApiClientMockBean;
    @Mock
    private CompanySearchResponseMapper companySearchResponseMapperMockBean;

    @InjectMocks
    private CompanySearchServiceImpl companySearchServiceImplTest;

    @Test
    public void companyAndOfficersLookupTest_SuccessWithCompanyNumber() throws Exception {

        CompanySearchResponse companySearchResponse = TestData.conStructCompanySearchResponse();

        when(truProxyApiClientMockBean.searchCompanies("apiKeyValue", TestData.constructCompanySearchRequest().getCompanyNumber()))
                .thenReturn(new ResponseEntity<>(TestData.constructSearchCompanies(), HttpStatus.OK));

        when(truProxyApiClientMockBean.getCompanyOfficersBasedOnCompanyNumber("apiKeyValue", TestData.constructCompanySearchRequest().getCompanyNumber()))
                .thenReturn(new ResponseEntity<>(TestData.constructOfficerList(), HttpStatus.OK));

        when(companySearchResponseMapperMockBean.mapToCompanySearchResponse((List<Item>) any(), (List<com.risknarrative.companySearchApplication.dto.officers.Item>) any()))
                .thenReturn(companySearchResponse);
        CompanySearchResponse companySearchResponseObject = companySearchServiceImplTest.companyAndOfficersLookup("apiKeyValue", Constants.ACTIVE, TestData.constructCompanySearchRequest());
        assertNotNull(companySearchResponseObject);
        assertEquals(companySearchResponse, companySearchResponseObject);
    }

    @Test
    public void companyAndOfficersLookupTest_SuccessWithCompanyName() throws Exception {

        CompanySearchResponse companySearchResponse = TestData.conStructCompanySearchResponse();

        when(truProxyApiClientMockBean.searchCompanies("apiKeyValue", TestData.constructCompanySearchRequestWithNameOnly().getCompanyName()))
                .thenReturn(new ResponseEntity<>(TestData.constructSearchCompanies(), HttpStatus.OK));

        when(truProxyApiClientMockBean.getCompanyOfficersBasedOnCompanyNumber("apiKeyValue", COMPANY_NUMBER))
                .thenReturn(new ResponseEntity<>(TestData.constructOfficerList(), HttpStatus.OK));

        when(companySearchResponseMapperMockBean.mapToCompanySearchResponse((List<Item>) any(), (List<com.risknarrative.companySearchApplication.dto.officers.Item>) any()))
                .thenReturn(companySearchResponse);
        CompanySearchResponse companySearchResponseObject = companySearchServiceImplTest.companyAndOfficersLookup("apiKeyValue", Constants.ACTIVE, TestData.constructCompanySearchRequestWithNameOnly());
        assertNotNull(companySearchResponseObject);
        assertEquals(companySearchResponse, companySearchResponseObject);
    }

    @Test
    public void companyAndOfficersLookupTest_Error400() throws Exception {

        CompanySearchResponse companySearchResponse = TestData.conStructCompanySearchResponse();

        when(truProxyApiClientMockBean.searchCompanies("apiKeyValue", TestData.constructCompanySearchRequest().getCompanyNumber()))
                .thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        when(truProxyApiClientMockBean.getCompanyOfficersBasedOnCompanyNumber("apiKeyValue", TestData.constructCompanySearchRequest().getCompanyNumber()))
                .thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        CompanySearchResponse companySearchResponseObject = companySearchServiceImplTest.companyAndOfficersLookup("apiKeyValue", Constants.ACTIVE, TestData.constructCompanySearchRequest());

        assertNull(companySearchResponseObject);
    }

}