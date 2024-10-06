package com.michaelrichardhall.company_search.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanySearchResponse {
    private int total_results;
    private List<Company> items;
}
