package com.michaelrichardhall.company_search.tru_proxy_api.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TruProxyAPISelfLink {
    private String self;
}
