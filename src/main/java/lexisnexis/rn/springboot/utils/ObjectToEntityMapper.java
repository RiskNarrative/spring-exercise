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

        return Company.builder().companyNumber(companyTruProxy.getCompany_number())
                .companyType(companyTruProxy.getCompany_type())
                .title(companyTruProxy.getTitle())
                .companyStatus(companyTruProxy.getCompany_status())
                .dateOfCreation(companyTruProxy.getDate_of_creation())
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
                .officers(companyEntity.getOfficers().stream()
                        .map(ObjectToEntityMapper::mapEntityToOfficer).collect(Collectors.toList()))
                .build();
    }

    public static CompanyEntity mapCompanyToEntity(Company company) {
        return CompanyEntity.builder().companyNumber(company.getCompanyNumber())
                .companyType(company.getCompanyType())
                .title(company.getTitle())
                .companyStatus(company.getCompanyStatus())
                .dateOfCreation(company.getDateOfCreation())
                .address(mapAddressToEntity(company.getAddress()))
                .officers(company.getOfficers().stream()
                        .map(ObjectToEntityMapper::mapOfficerToEntity).collect(Collectors.toList()))
                .build();
    }

    public static OfficerEntity mapOfficerToEntity(Officer officer) {

        return OfficerEntity.builder().name(officer.getName())
                .officerRole(officer.getOfficerRole())
                .appointedOn(officer.getAppointedOn())
                .address(mapAddressToEntity(officer.getAddress())).build();
    }

    public static AddressEntity mapAddressToEntity(Address address) {
        return AddressEntity.builder().locality(address.getLocality())
                .postalCode(address.getPostal_code())
                .premises(address.getPremises())
                .addressLine1(address.getAddress_line_1())
                .country(address.getCountry())
                .build();

    }

    public static Officer mapEntityToOfficer(OfficerEntity officerEntity) {

        return Officer.builder().name(officerEntity.getName())
                .officerRole(officerEntity.getOfficerRole())
                .appointedOn(officerEntity.getAppointedOn())
                .address(mapEntityToAddress(officerEntity.getAddress())).build();
    }

    public static Address mapEntityToAddress(AddressEntity addressEntity) {
        return Address.builder().locality(addressEntity.getLocality())
                .postal_code(addressEntity.getPostalCode())
                .premises(addressEntity.getPremises())
                .address_line_1(addressEntity.getAddressLine1())
                .country(addressEntity.getCountry())
                .build();

    }

}
