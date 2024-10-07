package com.risknarrative.companySearchApplication.response;

import com.risknarrative.companySearchApplication.dto.util.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Officer {
    private String name;
    private String officerRole;
    private String appointedOn;
    private Address address;

}
