package com.sachdeva.dto.mapper;

import com.sachdeva.dto.CompanyDetails;
import com.sachdeva.dto.Officer;
import com.sachdeva.dto.truproxy.CompanyResponse;

import java.util.List;
import java.util.stream.Collectors;

public class CompanyMapper {
        public static CompanyDetails mapCompanyAPIResponseToCompany(CompanyResponse apiCompany, List<Officer> officers) {
            CompanyDetails response = new CompanyDetails();

            response.setCompany_number(apiCompany.getCompany_number());
            response.setCompany_type(apiCompany.getCompany_type());
            response.setTitle(apiCompany.getTitle());
            response.setCompany_status(apiCompany.getCompany_status());
            response.setDate_of_creation(apiCompany.getDate_of_creation());
            response.setAddress(apiCompany.getAddress());
            response.setOfficers(officers);

            return response;
        }


}
