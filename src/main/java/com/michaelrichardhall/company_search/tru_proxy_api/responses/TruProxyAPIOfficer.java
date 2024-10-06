package com.michaelrichardhall.company_search.tru_proxy_api.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TruProxyAPIOfficer {
    private TruProxyAPIAddress address;
    private String name;
    private String appointed_on;
    private String resigned_on;
    private String officer_role;
    private TruProxyAPIOfficerLink links;
    private TruProxyAPIOfficerDateOfBirth date_of_birth;
    private String occupation;
    private String country_of_residence;
    private String nationality;
}
