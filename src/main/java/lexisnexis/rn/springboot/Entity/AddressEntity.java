package lexisnexis.rn.springboot.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class AddressEntity {
    @GeneratedValue
    @Id
    Long addressId;
    String premises;
    String locality;
    String addressLine1;
    String country;
    String postalCode;
}
