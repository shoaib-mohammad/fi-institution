package com.onboarding.fi.security.configs;

import com.onboarding.fi.constants.CommonConstants;
import com.onboarding.fi.enums.CustomEnums;
import com.onboarding.fi.security.components.TokenProvider;
import com.onboarding.fi.security.models.User;
import com.onboarding.fi.utils.CommonUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final UserDetailsService userDetailsService;
    private final TokenProvider jwtTokenUtil;

    public JwtAuthenticationFilter(TokenProvider jwtTokenUtil, UserDetailsService userDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        String token = null;
        String username = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authorization != null && authorization.startsWith(CommonConstants.BEARER)) {
            token = authorization.replace(CommonConstants.BEARER, "");
            try {
                username = jwtTokenUtil.getUsername(token);
            } catch (IllegalArgumentException e) {
                log.error("An error occurred during getting username from token", e);
            } catch (ExpiredJwtException e) {
                log.error("Token is expired", e);
            } catch (SignatureException e) {
                log.error("Authentication Failed, Username or Password not valid.", e);
            } catch (Exception e) {
                log.error("Outsource created token is used", e);
            }
        }
        if (!CommonUtils.isEmpty(username) && authentication == null) {
            User user = (User) userDetailsService.loadUserByUsername(username);
            if (jwtTokenUtil.isTokenValid(token, user) && !user.getStatus().equals(CustomEnums.Status.INACTIVE)) {
                Authentication authenticationToken = jwtTokenUtil.getAuthenticationToken(token, user);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        chain.doFilter(request, response);
    }

}
