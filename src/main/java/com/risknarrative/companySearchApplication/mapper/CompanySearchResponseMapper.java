package com.risknarrative.companySearchApplication.mapper;

import com.risknarrative.companySearchApplication.dto.companies.Item;
import com.risknarrative.companySearchApplication.response.CompanySearchResponse;
import com.risknarrative.companySearchApplication.response.Officer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Component
public class CompanySearchResponseMapper {

    public CompanySearchResponse mapToCompanySearchResponse(List<Item> companiesBasedOnStatus, List<com.risknarrative.companySearchApplication.dto.officers.Item> activeOfficers){
        List<Officer> activeOfficerOfficerListResponse = mapToActiveOfficerResponse(activeOfficers);
        return buildCompanyOfficerResponse(companiesBasedOnStatus, activeOfficerOfficerListResponse);
    }

    private List<Officer> mapToActiveOfficerResponse(List<com.risknarrative.companySearchApplication.dto.officers.Item> activeOfficers){
        List<Officer> activeOfficerOfficerListResponse = new ArrayList<>();
        if (nonNull(activeOfficers)) {
            for (com.risknarrative.companySearchApplication.dto.officers.Item activeOfficer : activeOfficers) {
                activeOfficerOfficerListResponse.add(Officer.builder().name(activeOfficer.getName())
                        .officerRole(activeOfficer.getOfficer_role())
                        .appointedOn(activeOfficer.getAppointed_on())
                        .address(activeOfficer.getAddress())
                        .build());
            }
        }
        return activeOfficerOfficerListResponse;
    }
    private CompanySearchResponse buildCompanyOfficerResponse(List<Item> companiesBasedOnStatus, List<Officer> officerList) {
        List<com.risknarrative.companySearchApplication.response.Item> companySearchResponseItems = new ArrayList<>();
        for(Item company : companiesBasedOnStatus) {
            company.getCompanyNumber();
            com.risknarrative.companySearchApplication.response.Item companySearchResponseItem = com.risknarrative.companySearchApplication.response.Item.builder()
                    .companyNumber(company.getCompanyNumber())
                    .companyType(company.getCompanyType())
                    .title(company.getTitle())
                    .companyStatus(company.getCompanyStatus())
                    .dateOfCreation(company.getDateOfCreation())
                    .address(company.getAddress())
                    .officers(officerList)
                    .build();
            companySearchResponseItems.add(companySearchResponseItem);
        }
        return CompanySearchResponse.builder().items(companySearchResponseItems).build();

    }


}
