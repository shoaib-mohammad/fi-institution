package com.onboarding.fi.institution.dtos;

import com.onboarding.fi.base.Identifiable;
import com.onboarding.fi.enums.CustomEnums;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class InstitutionRequestDTO implements Identifiable {
    private Long id = 0L;
    private String name;
    private String code;
    @ApiModelProperty(value = "Enabled/Disabled status", example = "ACTIVE")
    private CustomEnums.Status status = CustomEnums.Status.ACTIVE;
    private List<String> currencyList;

    @Override
    public Long getId() {
        return id;
    }
}
