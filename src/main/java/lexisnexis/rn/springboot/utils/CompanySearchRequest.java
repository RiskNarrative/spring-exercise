package lexisnexis.rn.springboot.utils;

import lombok.Data;

@Data
public class CompanySearchRequest {
    private String companyName;
    private String companyNumber;
}
