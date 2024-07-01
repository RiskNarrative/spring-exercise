package lexisnexis.rn.springboot.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Company {
    @Id
    String companyNumber;
    String companyType;
    String title;
    String companyStatus;
    String dateOfCreation;
    @OneToOne(cascade = CascadeType.ALL)
    Address address;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Officer> officers;
}
