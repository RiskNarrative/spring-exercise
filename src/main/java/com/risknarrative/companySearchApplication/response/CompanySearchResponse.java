package com.risknarrative.companySearchApplication.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
@Builder
public class CompanySearchResponse {
        private int totalResults;
        private List<Item> items;

   /* // Getters and Setters
        public int getTotalResults() {
            return totalResults;
        }

        public List<Item> getItems() {
            return items;
        }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }*/
}
