package com.example.SpringExercise.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Officer{
    private String name;
    private String officer_role;
    private String appointed_on;
    private Address address;


}