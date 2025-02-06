package com.onboarding.fi.security.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Login request dto")
public class LoginRequest {

    @ApiModelProperty(value = "Username", position = 1)
    private String username;

    @ApiModelProperty(value = "Password", position = 2)
    private String password;
}
