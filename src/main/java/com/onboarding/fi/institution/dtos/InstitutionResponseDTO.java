package com.onboarding.fi.institution.dtos;

import com.onboarding.fi.enums.CustomEnums;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class InstitutionResponseDTO {
    private Long id;
    private String name;
    private String code;
    private List<String> currencyList;
    private CustomEnums.Status status;
    private LocalDate dateCreated;
}
