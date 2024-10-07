package com.risknarrative.companySearchApplication.service;

import com.risknarrative.companySearchApplication.client.TruProxyApiClient;
import com.risknarrative.companySearchApplication.dto.companies.Item;
import com.risknarrative.companySearchApplication.dto.companies.SearchCompanies;
import com.risknarrative.companySearchApplication.dto.officers.OfficerList;
import com.risknarrative.companySearchApplication.mapper.CompanySearchResponseMapper;
import com.risknarrative.companySearchApplication.request.CompanySearchRequest;
import com.risknarrative.companySearchApplication.response.CompanySearchResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.risknarrative.companySearchApplication.Constants.ACTIVE;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class CompanySearchServiceImpl implements CompanySearchService {

    private static final Logger log = LoggerFactory.getLogger(CompanySearchServiceImpl.class);

    private final TruProxyApiClient truProxyApiClient;

    private final CompanySearchResponseMapper companySearchResponseMapper;

    @Override
    public CompanySearchResponse companyAndOfficersLookup(String apiKey, String companyStatus, CompanySearchRequest companySearchRequest) {
        log.info("companySearchRequest: {}", companySearchRequest);
        ResponseEntity<SearchCompanies> searchCompanies;
        ResponseEntity<OfficerList> officerList;
        if(nonNull(companySearchRequest.getCompanyNumber())) {
            searchCompanies = truProxyApiClient.searchCompanies(apiKey, companySearchRequest.getCompanyNumber());
            officerList = truProxyApiClient.getCompanyOfficersBasedOnCompanyNumber(apiKey, companySearchRequest.getCompanyNumber());
        }
        else{
            searchCompanies = truProxyApiClient.searchCompanies(apiKey, companySearchRequest.getCompanyName());
            String companyNumberFromName = null;
            if(nonNull(searchCompanies.getBody())) {
                List<Item> items = searchCompanies.getBody().getItems().stream().filter(item -> item.getTitle().equals(companySearchRequest.getCompanyName())).toList();
                searchCompanies.getBody().setItems(items);
                companyNumberFromName = items.get(0).getCompanyNumber();
            }
            officerList = truProxyApiClient.getCompanyOfficersBasedOnCompanyNumber(apiKey, companyNumberFromName);
        }
        List<Item> companiesBasedOnStatus  = getCompaniesBasedOnStatus(searchCompanies.getBody(), companyStatus);
        List<com.risknarrative.companySearchApplication.dto.officers.Item> activeOfficerOfficerListResponse = getActiveOfficerOfficerListResponse(officerList.getBody());
        CompanySearchResponse companySearchResponse = companySearchResponseMapper.mapToCompanySearchResponse(companiesBasedOnStatus, activeOfficerOfficerListResponse);
        log.info("companySearchResponse: {}", companySearchResponse);
        return companySearchResponse;
    }

    private List<Item> getCompaniesBasedOnStatus(SearchCompanies searchCompanies, String companyStatus) {
        List<Item> companies = new ArrayList<>();
        if (nonNull(searchCompanies)) {
            if (nonNull(companyStatus) && companyStatus.equals(ACTIVE)) {
                companies = searchCompanies.getItems()
                        .stream()
                        .filter(item -> nonNull(item.getCompanyStatus()) && item.getCompanyStatus().equals(companyStatus))
                        .collect(toList());
            } else {
                companies = searchCompanies.getItems()
                        .stream()
                        .filter(item -> nonNull(companyStatus)
                                ? nonNull(item.getCompanyStatus()) && item.getCompanyStatus().equals(companyStatus)
                                : nonNull(item.getCompanyStatus()))
                        .collect(toList());
            }
        }
        return companies;
    }

    private List<com.risknarrative.companySearchApplication.dto.officers.Item> getActiveOfficerOfficerListResponse(OfficerList officerList) {
        List<com.risknarrative.companySearchApplication.dto.officers.Item> officers = new ArrayList<>();
        if(nonNull(officerList)) {
            officers =
                    nonNull(officerList.getItems()) ? officerList.getItems()
                            .stream()
                            .filter(item -> isNull(item.getResigned_on())).toList() : null;
        }
        return officers;
    }
}
