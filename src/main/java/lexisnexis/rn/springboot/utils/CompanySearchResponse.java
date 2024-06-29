package lexisnexis.rn.springboot.utils;

import lexisnexis.rn.springboot.model.Company;
import lombok.Data;

import java.util.List;

@Data
public class CompanySearchResponse {
    private int totalResults;
    private List<Company> items;
}
