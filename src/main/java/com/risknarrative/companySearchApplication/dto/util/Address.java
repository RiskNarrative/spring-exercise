package com.risknarrative.companySearchApplication.dto.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Address {
        private String premises;
        private String postal_code;
        private String country;
        private String locality;
        private String address_line_1;
}
