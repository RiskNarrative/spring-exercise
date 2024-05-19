package com.example.SpringExercise.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Officer{
    private String name;
    private String officer_role;
    private String appointed_on;
    private Address address;


}