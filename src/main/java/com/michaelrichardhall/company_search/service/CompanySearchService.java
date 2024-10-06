package com.michaelrichardhall.company_search.service;

import com.michaelrichardhall.company_search.tru_proxy_api.gateway.Gateway;
import com.michaelrichardhall.company_search.tru_proxy_api.responses.TruProxyAPICompany;
import com.michaelrichardhall.company_search.tru_proxy_api.responses.TruProxyAPIOfficer;
import com.michaelrichardhall.company_search.web.dto.Company;
import com.michaelrichardhall.company_search.web.dto.Officer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class CompanySearchService {

    public static final String ACTIVE = "active";
    @Autowired
    private Gateway truAPIGateway;

    public List<Company> getCompanyByCompanyNameOrNumber(String companyName, String companyNumber, boolean activeOnly) {
        List<TruProxyAPICompany> truProxyAPICompanies = truAPIGateway.searchCompanies(companyName, companyNumber);
        List<Company> companyList = new ArrayList<>();
        if (truProxyAPICompanies == null) {
            return Collections.emptyList();
        } else {
            for (TruProxyAPICompany truProxyAPICompany : truProxyAPICompanies) {
                if (!activeOnly || ACTIVE.equals(truProxyAPICompany.getCompany_status())) {
                    List<TruProxyAPIOfficer> truProxyAPIOfficers = truAPIGateway.getCompanyOfficersByCompanyNumber(truProxyAPICompany.getCompany_number());
                    Company company = Company.getCompanyFromTruProxyAPICompany(truProxyAPICompany);
                    company.setOfficers(truProxyAPIOfficers.stream()
                            .filter(truProxyAPIOfficer -> truProxyAPIOfficer.getResigned_on() == null)
                            .map(Officer::getOfficerFromTruProxyAPIOfficer).toList());
                    companyList.add(company);
                }
            }
        }
        return companyList;
    }
}
