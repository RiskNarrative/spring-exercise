package com.example.SpringExercise.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class CompanyEntity {
    @Id
    private String companyNumber;
    private String companyType;
    private String title;
    private String companyStatus;
    private String dateOfCreation;
    @OneToOne(cascade = CascadeType.ALL)
    private AddressEntity address;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OfficerEntity> officers;

}