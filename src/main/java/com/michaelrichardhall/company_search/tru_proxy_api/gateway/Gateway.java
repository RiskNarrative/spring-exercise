package com.michaelrichardhall.company_search.tru_proxy_api.gateway;

import com.michaelrichardhall.company_search.tru_proxy_api.responses.TruProxyAPICompany;
import com.michaelrichardhall.company_search.tru_proxy_api.responses.TruProxyAPICompanyOfficersSearchResponse;
import com.michaelrichardhall.company_search.tru_proxy_api.responses.TruProxyAPICompanySearchResponse;
import com.michaelrichardhall.company_search.tru_proxy_api.responses.TruProxyAPIOfficer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class Gateway {
    private static final String COMPANY_SEARCH_ENDPOINT = "/TruProxyAPI/rest/Companies/v1/Search?Query={query}";
    private static final String OFFICERS_SEARCH_ENDPOINT = "/TruProxyAPI/rest/Companies/v1/Officers?CompanyNumber={number}";
    @Autowired
    RestClient restClient;


    public List<TruProxyAPICompany> searchCompanies(String companyName, String companyNumber) {
        String query = companyNumber != null ? companyNumber : companyName;
        TruProxyAPICompanySearchResponse response = restClient.get()
                .uri(COMPANY_SEARCH_ENDPOINT, query)
                .retrieve()
                .body(TruProxyAPICompanySearchResponse.class);

        return response.getItems();
    }

    public List<TruProxyAPIOfficer> getCompanyOfficersByCompanyNumber(String number) {
        TruProxyAPICompanyOfficersSearchResponse response = restClient.get()
                .uri(OFFICERS_SEARCH_ENDPOINT, number)
                .retrieve()
                .body(TruProxyAPICompanyOfficersSearchResponse.class);
        return response.getItems();
    }
}
