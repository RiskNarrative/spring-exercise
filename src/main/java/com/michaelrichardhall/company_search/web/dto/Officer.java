package com.michaelrichardhall.company_search.web.dto;

import com.michaelrichardhall.company_search.tru_proxy_api.responses.TruProxyAPIOfficer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Officer {
    private String name;
    private String officer_role;
    private String appointed_on;
    private Address address;

    public static Officer getOfficerFromTruProxyAPIOfficer(TruProxyAPIOfficer truProxyAPIOfficer) {
        return Officer.builder()
                .name(truProxyAPIOfficer.getName())
                .officer_role(truProxyAPIOfficer.getOfficer_role())
                .appointed_on(truProxyAPIOfficer.getAppointed_on())
                .address(Address.getAddressFromTruProxyAPIAddress(truProxyAPIOfficer.getAddress()))
                .build();
    }
}
