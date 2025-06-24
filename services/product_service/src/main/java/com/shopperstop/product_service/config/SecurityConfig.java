package com.shopperstop.product_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity.PrePostEnabled;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.security.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {





    @Bean
    public SecurityFilterChain productServiceSecurity(HttpSecurity http) throws Exception {
        http
                // 1) Completely disable CSRF protection
                .csrf(AbstractHttpConfigurer::disable)

                // 2) We’re using Basic auth for a REST API, so make it stateless
                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 3) Basic auth
                .httpBasic(Customizer.withDefaults())

                // 4) Lock down /product/**, let everything else through
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/product/**").authenticated()
                                .anyRequest().permitAll()
                );

        return http.build();
    }
}
