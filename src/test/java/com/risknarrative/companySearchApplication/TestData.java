package com.risknarrative.companySearchApplication;

import com.risknarrative.companySearchApplication.dto.companies.SearchCompanies;
import com.risknarrative.companySearchApplication.dto.officers.OfficerList;
import com.risknarrative.companySearchApplication.dto.util.Address;
import com.risknarrative.companySearchApplication.request.CompanySearchRequest;
import com.risknarrative.companySearchApplication.response.CompanySearchResponse;
import com.risknarrative.companySearchApplication.response.Item;

import java.util.ArrayList;
import java.util.List;

public class TestData {

    public static final String COMPANY_NUMBER = "12345678";
    public static final String COMPANY_NAME = "BBC Limited";
    public static final String COMPANY_STATUS = "active";
    public static final String API_KEY_VALUE = "apiKeyValue";
    public static final int WIREMOCK_PORT = 8080;

    public static CompanySearchRequest constructCompanySearchRequest() {
        CompanySearchRequest companySearchRequest = new CompanySearchRequest();
        companySearchRequest.setCompanyNumber(COMPANY_NUMBER);
        companySearchRequest.setCompanyName(COMPANY_NAME);
        return companySearchRequest;
    }

    public static CompanySearchRequest constructCompanySearchRequestWithNameOnly() {
        CompanySearchRequest companySearchRequest = new CompanySearchRequest();
        companySearchRequest.setCompanyName(COMPANY_NAME);
        return companySearchRequest;
    }

    public static CompanySearchResponse conStructCompanySearchResponse() {
        CompanySearchRequest companySearchRequest =constructCompanySearchRequest();
        List<Item> items = new ArrayList<Item>();
        Item item =  Item.builder()
               .companyNumber(companySearchRequest.getCompanyNumber())
               .title(companySearchRequest.getCompanyName())
               .companyType("Limited")
               .companyStatus(COMPANY_STATUS)
               .dateOfCreation("2020-01-01")
               .address(conStructAddress())
               .build();
        items.add(item);
     return CompanySearchResponse.builder().items(items).build();
    }

    public static OfficerList constructOfficerList() {
        OfficerList officerList = new OfficerList();
        com.risknarrative.companySearchApplication.dto.officers.Item item =  new com.risknarrative.companySearchApplication.dto.officers.Item();
        item.setName(COMPANY_NAME);
        item.setAddress(conStructAddress());
        item.setAppointed_on("2020-01-01");
        item.setOfficer_role("officer_role");
        List<com.risknarrative.companySearchApplication.dto.officers.Item> items = new ArrayList<>();
        items.add(item);
        officerList.setItems(items);
        return officerList;
    }

    public static SearchCompanies constructSearchCompanies() {
        SearchCompanies searchCompanies = new SearchCompanies();
        com.risknarrative.companySearchApplication.dto.companies.Item item =  new com.risknarrative.companySearchApplication.dto.companies.Item();
        item.setCompany_number(COMPANY_NUMBER);
        item.setTitle(COMPANY_NAME);
        item.setAddress(conStructAddress());
        item.setCompanyStatus(COMPANY_STATUS);
        List<com.risknarrative.companySearchApplication.dto.companies.Item> items = new ArrayList<>();
        items.add(item);
        searchCompanies.setItems(items);
        return searchCompanies;
    }

    public static Address conStructAddress() {
        return Address.builder().address_line_1("address_line_1").postal_code("postcode").country("uk").build();
    }
}
