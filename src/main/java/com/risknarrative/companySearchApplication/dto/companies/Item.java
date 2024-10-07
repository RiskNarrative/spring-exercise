package com.risknarrative.companySearchApplication.dto.companies;

import com.risknarrative.companySearchApplication.dto.util.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
public class Item {
    private String company_status;
    private String address_snippet;
    private String date_of_creation;
    private Matches matches;
    private String description;
    private Links links;
    private String company_number;
    private String title;
    private String company_type;
    private Address address;
    private String kind;
    private List<String> description_identifier;

    public String getCompanyStatus() {
        return company_status;
    }

    public void setCompanyStatus(String company_status) {
        this.company_status = company_status;
    }

    public String getAddressSnippet() {
        return address_snippet;
    }

    public void setAddressSnippet(String address_snippet) {
        this.address_snippet = address_snippet;
    }

    public String getDateOfCreation() {
        return date_of_creation;
    }

    public void setDateOfCreation(String date_of_creation) {
        this.date_of_creation = date_of_creation;
    }

    public Matches getMatches() {
        return matches;
    }

    public void setMatches(Matches matches) {
        this.matches = matches;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public String getCompanyNumber() {
        return company_number;
    }

    public void setCompanyNumber(String company_number) {
        this.company_number = company_number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompanyType() {
        return company_type;
    }

    public void setCompanyType(String company_type) {
        this.company_type = company_type;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public List<String> getDescriptionIdentifier() {
        return description_identifier;
    }

    public void setDescriptionIdentifier(List<String> description_identifier) {
        this.description_identifier = description_identifier;
    }
}

