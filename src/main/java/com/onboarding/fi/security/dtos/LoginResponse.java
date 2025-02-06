package com.onboarding.fi.security.dtos;

import com.onboarding.fi.enums.CustomEnums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private CustomEnums.Type userType;
}
