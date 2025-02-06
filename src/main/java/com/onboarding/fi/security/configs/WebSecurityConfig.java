package com.onboarding.fi.security.configs;

import com.onboarding.fi.constants.PermissibleUrlsConstant;
import com.onboarding.fi.security.components.TokenProvider;
import com.onboarding.fi.security.components.UnauthorizedEntryPoint;
import com.onboarding.fi.security.models.User;
import com.onboarding.fi.security.services.authentication.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfiguration {

    @Autowired
    private UnauthorizedEntryPoint unauthorizedEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, TokenProvider tokenProvider) throws Exception {
        http.httpBasic().disable()
                .cors().and().csrf().disable()
                .authorizeHttpRequests()
                .antMatchers(PermissibleUrlsConstant.skipUrls).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(new JwtAuthenticationFilter(tokenProvider, userDetailsService()),
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(UserService userService, BCryptPasswordEncoder passwordEncoder) throws Exception {
        return authentication -> {
            String username = authentication.getPrincipal().toString();
            User user = (User) userService.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(username, "", user.getAuthorities());
        };
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigure() {
        return new WebMvcConfigurer() {
            @Value("#{'${spring.cors.allowed-origins}'.split(',')}")
            private List<String> allowedOrigins;

            @Value("#{'${spring.cors.allowed-methods}'.split(',')}")
            private List<String> allowedMethods;

            @Value("#{'${spring.cors.allowed-headers}'.split(',')}")
            private List<String> allowedHeaders;

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(allowedOrigins.toArray(new String[0]))
                        .allowedMethods(allowedMethods.toArray(new String[0]))
                        .allowedHeaders(allowedHeaders.toArray(new String[0]))
                        .allowCredentials(true);
            }
        };
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserService();
    }
}
