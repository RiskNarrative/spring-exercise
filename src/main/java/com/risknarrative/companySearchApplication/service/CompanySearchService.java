package com.risknarrative.companySearchApplication.service;

import com.risknarrative.companySearchApplication.request.CompanySearchRequest;
import com.risknarrative.companySearchApplication.response.CompanySearchResponse;

public interface CompanySearchService {
   CompanySearchResponse companyAndOfficersLookup(String apiKey, String companyStatus, CompanySearchRequest companySearchRequest);
}
