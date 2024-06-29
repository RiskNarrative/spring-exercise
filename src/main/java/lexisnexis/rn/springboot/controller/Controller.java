package lexisnexis.rn.springboot.controller;

import com.google.gson.GsonBuilder;
import com.google.json.JsonSanitizer;
import lexisnexis.rn.springboot.service.CompanyService;
import lexisnexis.rn.springboot.utils.CompanySearchRequest;
import lexisnexis.rn.springboot.utils.CompanySearchResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/companies")
public class Controller {

    private final CompanyService companyService;

    @Autowired
    public Controller(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/search")
    public ResponseEntity<CompanySearchResponse> searchCompanies(@RequestHeader("x-api-key") String apiKey,
                                                                 @RequestParam(defaultValue = "true") boolean activeOnly,
                                                                 final @RequestBody String jsonInput) throws Exception {

        log.info("Received request {}", jsonInput);
        CompanySearchRequest request = getSearchRequest(jsonInput);
        CompanySearchResponse response = companyService.findCompany(apiKey, request, activeOnly);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/hello")
    public String returnHello() {
        return "print me";

    }

    private CompanySearchRequest getSearchRequest(String jsonInput) {
        return new GsonBuilder().create().fromJson(JsonSanitizer.sanitize(jsonInput), CompanySearchRequest.class);
    }

}
