package com.onboarding.fi.constants;


public class PermissibleUrlsConstant {

    public static final String[] skipUrls = new String[]{"/authentication", "/swagger-ui.html",
            "/v2/api-docs",
            "/swagger-resources/**",
            "*/springfox-swagger-ui/*",
            "/webjars/springfox-swagger-ui/**"};
}
