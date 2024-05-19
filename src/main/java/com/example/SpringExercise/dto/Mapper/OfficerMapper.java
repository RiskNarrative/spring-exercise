package com.example.SpringExercise.dto.Mapper;

import com.example.SpringExercise.dto.Officer;
import com.example.SpringExercise.dto.truProxy.OfficerResponse;

public class OfficerMapper {
    public static Officer mapOfficerAPIResponseToOfficer(OfficerResponse trueProxyOfficer) {

        return Officer.builder().officer_role(trueProxyOfficer.getOfficer_role())
                .name(trueProxyOfficer.getName())
                .appointed_on(trueProxyOfficer.getAppointed_on())
                .address(trueProxyOfficer.getAddress()).build();

    }
}