package com.sachdeva.dto.truproxy;

import com.sachdeva.dto.truproxy.CompanyResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CompanySearchResult {
    private int total_results;
    private List<CompanyResponse> items;

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public List<CompanyResponse> getItems() {
        return items;
    }

    public void setItems(List<CompanyResponse> items) {
        this.items = items;
    }
}
