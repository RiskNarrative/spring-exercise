package lexisnexis.rn.springboot.utils;

import lexisnexis.rn.springboot.model.Address;
import lombok.Data;

@Data
public class CompanyTruProxy {
    private String companyStatus;
    private String dateOfCreation;
    private String companyNumber;
    private String title;
    private String companyType;
    private Address address;
    private String description;
}
