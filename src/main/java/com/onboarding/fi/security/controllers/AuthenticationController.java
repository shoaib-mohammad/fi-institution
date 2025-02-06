package com.onboarding.fi.security.controllers;

import com.onboarding.fi.dto.RestCommonResponseDTO;
import com.onboarding.fi.exceptions.CustomServiceException;
import com.onboarding.fi.security.dtos.LoginRequest;
import com.onboarding.fi.security.dtos.LoginResponse;
import com.onboarding.fi.security.services.authentication.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authorizationService;

    @PostMapping
    public RestCommonResponseDTO<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws CustomServiceException {
        return new RestCommonResponseDTO<>(true, authorizationService.login(loginRequest));
    }
}
