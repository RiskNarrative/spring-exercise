package com.michaelrichardhall.company_search.tru_proxy_api.responses;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TruProxyAPICompanyOfficersSearchResponse {
    private String etag;
    private TruProxyAPISelfLink links;
    private String kind;
    private int items_per_page;
    private List<TruProxyAPIOfficer> items;
}
