package lexisnexis.rn.springboot.utils;

import lexisnexis.rn.springboot.model.Address;
import lombok.Data;

@Data
public class CompanyTruProxy {
    private String company_number;
    private String company_type;
    private String title;
    private String company_status;
    private String date_of_creation;
    private Address address;
}
