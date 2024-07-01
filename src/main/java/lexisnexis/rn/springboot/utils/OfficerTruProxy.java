package lexisnexis.rn.springboot.utils;

import lexisnexis.rn.springboot.model.Address;
import lombok.Data;

@Data
public class OfficerTruProxy {

    private String name;
    private String officer_role;
    private String appointed_on;
    private String resigned_on;
    private Address address;
}
