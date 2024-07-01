package lexisnexis.rn.springboot.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressEntity {
    @GeneratedValue
    @Id
    Long addressId;
    String locality;
    String postalCode;
    String premises;
    String addressLine1;
    String country;
}
