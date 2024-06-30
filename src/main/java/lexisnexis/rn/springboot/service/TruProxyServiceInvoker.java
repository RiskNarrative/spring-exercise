package lexisnexis.rn.springboot.service;

import lexisnexis.rn.springboot.utils.CompanySearchRequest;
import lexisnexis.rn.springboot.utils.TruProxyResultCompany;
import lexisnexis.rn.springboot.utils.TruProxyResultOfficer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@Slf4j
public class TruProxyServiceInvoker {


    @Autowired
    RestTemplate restTemplate;

    @Value("${truproxy.baseUrl}")
    String baseUrl;

    public ResponseEntity<TruProxyResultCompany> searchCompanies(String apiKey, CompanySearchRequest request) {
        try {
            URI endpoint = getUriForCompanySearch(request.getCompanyName(), request.getCompanyNumber());

            HttpHeaders headers = new HttpHeaders();
            headers.set("x-api-key", apiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            log.info("Hitting TruProxy API with url: " + endpoint + " and headers: " + headers);
            return restTemplate.exchange(
                    endpoint,
                    HttpMethod.GET,
                    entity,
                    TruProxyResultCompany.class
            );
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO could be redundant if we refactor searchCompanies to be generic
    public ResponseEntity<TruProxyResultOfficer> searchOfficers(String apiKey, String companyNumber) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-api-key", apiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            log.info("Hitting TruProxy API with url: " + getUriForOfficerSearch(companyNumber) + " and headers: " + headers);
            return restTemplate.exchange(
                    getUriForOfficerSearch(companyNumber),
                    HttpMethod.GET,
                    entity,
                    TruProxyResultOfficer.class
            );
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    private URI getUriForCompanySearch(String name, String number) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + "/Search");

        if (StringUtils.hasLength(number)) {
            builder.queryParam("Query", number);
        } else {
            builder.queryParam("Query", name);
        }

        return builder.build().toUri();
    }

    //TODO merge  getUriForOfficerSearch and getUriForOfficerSearch
    private URI getUriForOfficerSearch(String number) {
        return UriComponentsBuilder.fromHttpUrl(baseUrl + "/Officers").queryParam("CompanyNumber", number).build().toUri();
    }
}
