package com.risknarrative.companySearchApplication.response;

import com.risknarrative.companySearchApplication.dto.util.Address;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Item {
    private String companyNumber;
    private String companyType;
    private String title;
    private String companyStatus;
    private String dateOfCreation;
    private Address address;
    private List<Officer> officers;
}
