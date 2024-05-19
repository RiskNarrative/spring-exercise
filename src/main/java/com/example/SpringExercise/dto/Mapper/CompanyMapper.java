package com.example.SpringExercise.dto.Mapper;

import com.example.SpringExercise.dto.CompanyDetails;
import com.example.SpringExercise.dto.Officer;
import com.example.SpringExercise.dto.truProxy.CompanyResponse;

import java.util.List;

public class CompanyMapper {
    public static CompanyDetails mapCompanyAPIResponseToCompany(CompanyResponse trueProxyCompany, List<Officer> officers) {

        return CompanyDetails.builder().companyNumber(trueProxyCompany.getCompany_number())
                .companyStatus(trueProxyCompany.getCompany_status())
                .companyType(trueProxyCompany.getCompany_type())
                .title(trueProxyCompany.getTitle())
                .dateOfCreation(trueProxyCompany.getDate_of_creation())
                .address(trueProxyCompany.getAddress())
                .officers(officers).build();
    }
}