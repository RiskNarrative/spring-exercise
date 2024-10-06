package com.michaelrichardhall.company_search.web.dto;

import com.michaelrichardhall.company_search.tru_proxy_api.responses.TruProxyAPIAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String locality;
    private String postal_code;
    private String premises;
    private String address_line_1;
    private String country;

    public static Address getAddressFromTruProxyAPIAddress(TruProxyAPIAddress truProxyAPIAddress) {
        return Address.builder()
                .locality(truProxyAPIAddress.getLocality())
                .postal_code(truProxyAPIAddress.getPostal_code())
                .premises(truProxyAPIAddress.getPremises())
                .address_line_1(truProxyAPIAddress.getAddress_line_1())
                .country(truProxyAPIAddress.getCountry())
                .build();
    }
}
