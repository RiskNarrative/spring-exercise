package lexisnexis.rn.springboot.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Officer {

    String name;
    String officerRole;
    String appointedOn;
    @OneToOne(cascade = CascadeType.ALL)
    Address address;

}
