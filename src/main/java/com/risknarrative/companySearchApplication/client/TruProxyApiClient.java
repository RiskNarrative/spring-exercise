package com.risknarrative.companySearchApplication.client;

import com.risknarrative.companySearchApplication.dto.companies.SearchCompanies;
import com.risknarrative.companySearchApplication.dto.officers.OfficerList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import static com.risknarrative.companySearchApplication.Constants.APIKEY_HEADER;

@FeignClient(name="tru-proxy-api-service", url="${application.config.truProxyUrl}")
public interface TruProxyApiClient {

    @GetMapping(path="/Search", produces = "application/json")
    ResponseEntity<SearchCompanies> searchCompanies(@RequestHeader(APIKEY_HEADER) String apiKey, @RequestParam(value = "Query") String search_term);

    @GetMapping(path="/Officers", produces = "application/json")
    ResponseEntity<OfficerList> getCompanyOfficersBasedOnCompanyNumber(@RequestHeader(APIKEY_HEADER) String apiKey, @RequestParam(value="CompanyNumber") String number);

}
