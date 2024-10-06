package com.michaelrichardhall.company_search.tru_proxy_api.responses;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TruProxyAPICompany {
    private String company_status;
    private String address_snippet;
    private String date_of_creation;
    private TruProxyAPIMatches matches;
    private String description;
    private TruProxyAPISelfLink links;
    private String company_number;
    private String title;
    private String company_type;
    private TruProxyAPIAddress address;
    private String kind;
    private List<String> description_identifier;
}
