package lexisnexis.rn.springboot.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {

    String locality;
    String postal_code;
    String premises;
    String address_line_1;
    String country;
}
