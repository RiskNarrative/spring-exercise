package com.michaelrichardhall.company_search.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@Valid
public class CompanySearchRequest {
    @NotNull
    private String companyName;
    private String companyNumber;
}
