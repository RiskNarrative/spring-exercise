package lexisnexis.rn.springboot.utils;

import lombok.Data;

import java.util.List;

@Data
public class TruProxyResultCompany {
    private int total_results;
    private List<CompanyTruProxy> items;

}
