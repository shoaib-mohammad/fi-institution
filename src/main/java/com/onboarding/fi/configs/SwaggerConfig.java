package com.onboarding.fi.configs;

import com.fasterxml.classmate.TypeResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket apiDocket() {
        TypeResolver typeResolver = new TypeResolver();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .alternateTypeRules(AlternateTypeRules.newRule(LocalDate.class, Date.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(List.class, LocalDate.class),
                        typeResolver.resolve(List.class, Date.class), Ordered.HIGHEST_PRECEDENCE))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.onboarding.fi"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo())
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(List.of(apiKey()));
        log.debug("Docket initialized");
        return docket;
    }


    private ApiInfo getApiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Fi Onboarding",
                "Financial Institution Onboarding",
                "1.0",
                "Terms of Service",
                null,
                "",
                "",
                Collections.emptyList());
        log.debug("ApiInfo initialized");
        return apiInfo;
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", HttpHeaders.AUTHORIZATION, "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(new SecurityReference("JWT", authorizationScopes));
    }

}

