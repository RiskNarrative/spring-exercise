package lexisnexis.rn.springboot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {

    Long addressId;
    String premises;
    String locality;
    String addressLine1;
    String country;
    String postalCode;
}
