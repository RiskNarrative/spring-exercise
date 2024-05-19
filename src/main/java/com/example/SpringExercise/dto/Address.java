package com.example.SpringExercise.dto;

import lombok.Builder;

@Builder
public record Address( String locality,
                       String postal_code,
                       String premises,
                       String address_line_1,
                       String country ) {
}