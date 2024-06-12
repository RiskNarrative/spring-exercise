package com.example.SpringExercise.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class Address {
    String locality;
    String postal_code;
    String premises;
    String address_line_1;
    String country;
}