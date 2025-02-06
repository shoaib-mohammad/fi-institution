package com.onboarding.fi.security.services.authentication;

import com.onboarding.fi.constants.CommonConstants;
import com.onboarding.fi.enums.CustomEnums;
import com.onboarding.fi.enums.ErrorResponseApisEnum;
import com.onboarding.fi.exceptions.CustomServiceException;
import com.onboarding.fi.security.components.TokenProvider;
import com.onboarding.fi.security.dtos.LoginRequest;
import com.onboarding.fi.security.dtos.LoginResponse;
import com.onboarding.fi.security.models.User;
import com.onboarding.fi.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;

    @Qualifier("userDetailsService")
    private final UserService userService;

    private final TokenProvider jwtTokenUtil;

    private final BCryptPasswordEncoder passwordEncoder;

    public LoginResponse login(@RequestBody LoginRequest loginRequest) throws CustomServiceException {
        if (CommonUtils.isEmpty(loginRequest.getUsername())) {
            throw new CustomServiceException(ErrorResponseApisEnum.MissingParameter, "cannot.be.null.or.empty.key", new String[]{"user.name.key"});
        }
        if (CommonUtils.isEmpty(loginRequest.getPassword())) {
            throw new CustomServiceException(ErrorResponseApisEnum.MissingParameter, "cannot.be.null.or.empty.key", new String[]{"password.key"});
        }
        User user = (User) userService.loadUserByUsername(loginRequest.getUsername());
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()) || user.getStatus().equals(CustomEnums.Status.INACTIVE)) {
            throw new CustomServiceException(ErrorResponseApisEnum.InvalidUser);
        }
        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = CommonConstants.BEARER.concat(jwtTokenUtil.generateToken(authentication));
        return new LoginResponse(token, user.getRole().getRoleType());
    }
}
