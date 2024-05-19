package com.example.SpringExercise.dto.truProxy;

import com.example.SpringExercise.dto.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyResponse {
    public String getCompany_status() {
        return company_status;
    }

    public void setCompany_status(String company_status) {
        this.company_status = company_status;
    }

    public String getDate_of_creation() {
        return date_of_creation;
    }

    public void setDate_of_creation(String date_of_creation) {
        this.date_of_creation = date_of_creation;
    }

    public String getCompany_number() {
        return company_number;
    }

    public void setCompany_number(String company_number) {
        this.company_number = company_number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany_type() {
        return company_type;
    }

    public void setCompany_type(String company_type) {
        this.company_type = company_type;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Map<String, List<Integer>> getMatches() {
        return matches;
    }

    public void setMatches(Map<String, List<Integer>> matches) {
        this.matches = matches;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String company_status;
    private String date_of_creation;
    private String company_number;
    private String title;
    private String company_type;
    private Address address;
    private Map<String, List<Integer>> matches;
    private String description;

}