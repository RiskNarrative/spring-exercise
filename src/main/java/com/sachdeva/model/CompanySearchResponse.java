package com.sachdeva.model;

import com.sachdeva.dto.CompanyDetails;
import com.sachdeva.dto.truproxy.CompanyResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class CompanySearchResponse {
    private int totalResults;
    private List<CompanyDetails> items;

    public boolean addItems(CompanyDetails companyDetails){
        if(items == null) {
            items = new ArrayList<>();
        }
        return items.add(companyDetails);
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<CompanyDetails> getItems() {
        return items;
    }

    public void setItems(List<CompanyDetails> items) {
        this.items = items;
    }
}
