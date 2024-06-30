package lexisnexis.rn.springboot.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    private List<OfficerEntity> officers;


}
