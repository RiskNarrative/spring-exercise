package com.risknarrative.companySearchApplication.dto.companies;

import lombok.Data;

import java.util.List;

@Data
public class SearchCompanies {
    private int page_number;
    private String kind;
    private int total_results;
    private List<Item> items;
}
