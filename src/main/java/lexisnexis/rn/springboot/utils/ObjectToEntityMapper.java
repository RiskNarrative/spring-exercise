package lexisnexis.rn.springboot.utils;

import lexisnexis.rn.springboot.Entity.AddressEntity;
import lexisnexis.rn.springboot.Entity.CompanyEntity;
import lexisnexis.rn.springboot.Entity.OfficerEntity;
import lexisnexis.rn.springboot.model.Address;
import lexisnexis.rn.springboot.model.Company;
import lexisnexis.rn.springboot.model.Officer;

import java.util.List;
import java.util.stream.Collectors;

public class ObjectToEntityMapper {

    public static Officer mapTruProxyOfficerToOfficer(OfficerTruProxy officerTruProxy) {

        return Officer.builder().officerRole(officerTruProxy.getOfficer_role())
                .name(officerTruProxy.getName())
                .appointedOn(officerTruProxy.getAppointed_on())
                .address(officerTruProxy.getAddress()).build();

    }

    public static Company mapTruProxyCompanyToCompany(CompanyTruProxy companyTruProxy, List<Officer> officerList) {

        return Company.builder().companyNumber(companyTruProxy.getCompanyNumber())
                .companyStatus(companyTruProxy.getCompanyStatus())
                .companyType(companyTruProxy.getCompanyType())
                .title(companyTruProxy.getTitle())
                .dateOfCreation(companyTruProxy.getDateOfCreation())
                .address(companyTruProxy.getAddress())
                .officers(officerList).build();

    }

    public static Company mapEntityToCompany(CompanyEntity companyEntity) {
        return Company.builder().companyNumber(companyEntity.getCompanyNumber())
                .companyStatus(companyEntity.getCompanyStatus())
                .companyType(companyEntity.getCompanyType())
                .title(companyEntity.getTitle())
                .dateOfCreation(companyEntity.getDateOfCreation())
                .address(mapEntityToAddress(companyEntity.getAddress()))
                .officers(companyEntity.getOfficerEntities().stream()
                        .map(ObjectToEntityMapper::mapEntityToOfficer).collect(Collectors.toList()))
                .build();

    }

    public static Officer mapEntityToOfficer(OfficerEntity officerEntity) {

        return Officer.builder().officerRole(officerEntity.getOfficeRole())
                .name(officerEntity.getName())
                .appointedOn(officerEntity.getAppointedOn())
                .address(mapEntityToAddress(officerEntity.getAddress())).build();

    }

    public static Address mapEntityToAddress(AddressEntity addressEntity) {
        return Address.builder().addressId(addressEntity.getAddressId())
                .country(addressEntity.getCountry())
                .addressLine1(addressEntity.getAddressLine1())
                .premises(addressEntity.getPremises())
                .postalCode(addressEntity.getPostalCode())
                .locality(addressEntity.getLocality()).build();

    }

}
