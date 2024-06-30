package lexisnexis.rn.springboot.service;

import lexisnexis.rn.springboot.Entity.CompanyEntity;
import lexisnexis.rn.springboot.model.Company;
import lexisnexis.rn.springboot.model.Officer;
import lexisnexis.rn.springboot.repository.CompanyRepository;
import lexisnexis.rn.springboot.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CompanyService {

    @Autowired
    private TruProxyServiceInvoker truProxyServiceInvoker;

    @Autowired
    CompanyRepository companyRepository;

    Predicate<CompanyTruProxy> isActiveCompany = (company) -> company.getCompany_status().equalsIgnoreCase("ACTIVE");
    Predicate<OfficerTruProxy> isActiveOfficer = (officer) -> !StringUtils.hasLength(officer.getResigned_on());

    public CompanySearchResponse findCompany(String apiKey, CompanySearchRequest request) throws Exception {
        validateInput(request);
        CompanySearchResponse response = new CompanySearchResponse();
        // first find in local database
        try {
            if (request != null && StringUtils.hasLength(request.getCompanyNumber())) {
                Optional<CompanyEntity> optionalCompany = companyRepository.findById(request.getCompanyNumber());
                if (optionalCompany.isPresent()) {
                    log.info("Found company in local repository");
                    CompanyEntity companyEntity = optionalCompany.get();
                    response = buildResponse(companyEntity);
                } else {
                    //fetch from TruProxy API if not found in the database
                    ResponseEntity<TruProxyResultCompany> apiResponse = truProxyServiceInvoker.searchCompanies(apiKey, request);
                    log.info("TruProxy api Response: {}", apiResponse);
                    List<CompanyTruProxy> activeCompanies = filterActive(apiResponse);

                    response.setTotalResults(activeCompanies.size());

                    List<Company> companies = new ArrayList<>();
                    for (CompanyTruProxy companyTruProxy : activeCompanies) {
                        List<Officer> officers = findOfficers(apiKey, companyTruProxy.getCompany_number());
                        Company company = ObjectToEntityMapper.mapTruProxyCompanyToCompany(companyTruProxy, officers);
                        companies.add(company);
                        companyRepository.save(ObjectToEntityMapper.mapCompanyToEntity(company));
                    }
                    response.setItems(companies);
                }
            }
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error fetching data from TruProxyAPI: " + e.getStatusCode());
        }
        return response;
    }

    private List<Officer> findOfficers(String apiKey, String companyNumber) {
        ResponseEntity<TruProxyResultOfficer> officersResults = truProxyServiceInvoker.searchOfficers(apiKey, companyNumber);

        return Objects.requireNonNull(officersResults.getBody())
                .getItems()
                .stream()
                .filter(o -> isActiveOfficer.test(o))
                .map(ObjectToEntityMapper::mapTruProxyOfficerToOfficer)
                .collect(Collectors.toList());
    }

    private CompanySearchResponse buildResponse(CompanyEntity companyEntity) {
        CompanySearchResponse companySearchResponse = new CompanySearchResponse();
        companySearchResponse.setItems(Collections.singletonList(ObjectToEntityMapper.mapEntityToCompany(companyEntity)));
        companySearchResponse.setTotalResults(1);
        return companySearchResponse;
    }

    private void validateInput(CompanySearchRequest request) throws Exception {
        if (request != null && !StringUtils.hasLength(request.getCompanyNumber())) {
            throw new Exception("Invalid input ");
        }
    }

    public List<CompanyTruProxy> filterActive(ResponseEntity<TruProxyResultCompany> response) {
        List<CompanyTruProxy> companies = Objects.requireNonNull(response.getBody()).getItems();
        log.info("companies list from truProxy {}", companies);
        if (companies == null || companies.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "No company found.");
        }

        return companies.stream()
                .filter(c -> isActiveCompany.test(c)).collect(Collectors.toList());
    }
}
