package com.example.SpringExercise.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class AddressEntity {
    @GeneratedValue
    @Id
    Long addressId;
    String locality;
    String postal_code;
    String premises;
    String address_line_1;
    String country;
}