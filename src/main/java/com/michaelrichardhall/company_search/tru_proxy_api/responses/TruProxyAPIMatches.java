package com.michaelrichardhall.company_search.tru_proxy_api.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TruProxyAPIMatches {
    private List<Integer> title;
}
