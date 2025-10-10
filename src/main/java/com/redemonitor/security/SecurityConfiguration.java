package com.redemonitor.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    private final String[] PUBLIC = {
            "/api/v1/login",

            "/swagger-ui/**",
            "/v3/api-docs/**",

            "/v3/api-docs",
            "/swagger-ui.html",
    };

    @Autowired
    private AuthorizationFilter2 authorizationFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf( csrf -> csrf.disable() )
                .cors( Customizer.withDefaults() )
                .authorizeHttpRequests(authHttpReqs ->
                        authHttpReqs
                                .requestMatchers( PUBLIC ).permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore( authorizationFilter, UsernamePasswordAuthenticationFilter.class );

        return http.build();
    }

}
