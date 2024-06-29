package lexisnexis.rn.springboot.Entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class OfficerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String name;
    String officeRole;
    String appointedOn;
    @OneToOne(cascade = CascadeType.ALL)
    AddressEntity address;

}
