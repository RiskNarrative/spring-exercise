package com.michaelrichardhall.company_search.web.dto;

import com.michaelrichardhall.company_search.tru_proxy_api.responses.TruProxyAPICompany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    private String company_number;
    private String company_type;
    private String title;
    private String company_status;
    private String date_of_creation;
    private Address address;
    private List<Officer> officers;

    public static Company getCompanyFromTruProxyAPICompany(TruProxyAPICompany truProxyAPICompany) {
        return Company.builder()
                .company_number(truProxyAPICompany.getCompany_number())
                .company_type(truProxyAPICompany.getCompany_type())
                .title(truProxyAPICompany.getTitle())
                .company_status(truProxyAPICompany.getCompany_status())
                .date_of_creation(truProxyAPICompany.getDate_of_creation())
                .address(Address.getAddressFromTruProxyAPIAddress(truProxyAPICompany.getAddress()))
                .build();
    }

}
