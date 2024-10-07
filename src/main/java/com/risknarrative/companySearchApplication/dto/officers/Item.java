package com.risknarrative.companySearchApplication.dto.officers;

import com.risknarrative.companySearchApplication.dto.util.Address;
import com.risknarrative.companySearchApplication.dto.util.DateOfBirth;
import lombok.Data;

@Data
public class Item {
        private Address address;
        private String name;
        private String appointed_on;
        private String resigned_on;
        private String officer_role;
        private ItemLinks links;
        private DateOfBirth date_of_birth;
        private String occupation;
        private String country_of_residence;
        private String nationality;
}
