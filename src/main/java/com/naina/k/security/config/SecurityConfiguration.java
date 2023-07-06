package com.naina.k.security.config;


import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.naina.k.security.user.Permission.*;
import static com.naina.k.security.user.Role.ACCOUNTANT;
import static com.naina.k.security.user.Role.ADMIN;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {


    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/authenticate", "/api/v1/auth/register")
                .permitAll()

                .requestMatchers("/api/v1/accountant**").hasAnyRole(ADMIN.name(), ACCOUNTANT.name())


                .requestMatchers(GET, "/api/v1/accountant**").hasAnyAuthority(ADMIN_READ.name(), ACCOUNTANT_READ.name())
                .requestMatchers(POST, "/api/v1/accountant**").hasAnyAuthority(ADMIN_CREATE.name(), ACCOUNTANT_CREATE.name())
                .requestMatchers(PUT, "/api/v1/accountant**").hasAnyAuthority(ADMIN_UPDATE.name(), ACCOUNTANT_UPDATE.name())
                .requestMatchers(DELETE, "/api/v1/accountant**").hasAnyAuthority(ADMIN_DELETE.name(), ACCOUNTANT_DELETE.name())

                .requestMatchers("/api/v1/admin**").hasRole(ADMIN.name())

                .requestMatchers(GET, "/api/v1/admin**").hasAuthority(ADMIN_READ.name())
                .requestMatchers(POST, "/api/v1/admin**").hasAuthority(ADMIN_CREATE.name())
                .requestMatchers(PUT, "/api/v1/admin**").hasAuthority(ADMIN_UPDATE.name())
                .requestMatchers(DELETE, "/api/v1/admin**").hasAuthority(ADMIN_DELETE.name())


                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
}
