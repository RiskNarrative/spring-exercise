package com.risknarrative.companySearchApplication.dto.officers;

import lombok.Data;

import java.util.List;

@Data
public class OfficerList {
    private String etag;
    private Links links;
    private String kind;
    private int items_per_page;
    private List<Item> items;
}
