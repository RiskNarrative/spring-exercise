package lexisnexis.rn.springboot.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
@Entity
public class CompanyEntity {
    @Id
    String companyNumber;
    String companyType;
    String title;
    String companyStatus;
    String dateOfCreation;
    @OneToOne(cascade = CascadeType.ALL)
    AddressEntity address;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OfficerEntity> officerEntities;


}
