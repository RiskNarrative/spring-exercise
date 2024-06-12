package com.example.SpringExercise.Model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


public class CompanySearchRequest {
    private String companyName;
    private String companyNumber;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }


}