package com.michaelrichardhall.company_search.tru_proxy_api.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TruProxyAPIOfficerDateOfBirth {
    private String month;
    private String year;
}
