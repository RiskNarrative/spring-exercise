package com.sachdeva.dto.mapper;

import com.sachdeva.dto.CompanyDetails;
import com.sachdeva.dto.Officer;
import com.sachdeva.dto.truproxy.CompanyResponse;
import com.sachdeva.dto.truproxy.OfficerResponse;

import java.util.List;

public class OfficerMapper {
    public static Officer mapOfficerAPIResponseToOfficer(OfficerResponse apiOfficer) {
        Officer officer = new Officer();
        officer.setName(apiOfficer.getName());
        officer.setOfficer_role(apiOfficer.getOfficer_role());
        officer.setAddress(apiOfficer.getAddress());
        officer.setAppointed_on(apiOfficer.getAppointed_on());
        return officer;
    }
}
