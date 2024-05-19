package com.example.SpringExercise.Entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class OfficerEntity {
    @GeneratedValue
    @Id
    Long officerId;
    private String name;
    private String officer_role;
    private String appointed_on;
    @OneToOne(cascade = CascadeType.ALL)
    private AddressEntity address;

}