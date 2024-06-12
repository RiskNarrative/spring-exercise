package com.example.SpringExercise.dto.truProxy;

import java.util.List;

public class OfficerSearchResult {
    private List<OfficerResponse> items;

    public List<OfficerResponse> getItems() {
        return items;
    }

    public void setItems(List<OfficerResponse> items) {
        this.items = items;
    }
}