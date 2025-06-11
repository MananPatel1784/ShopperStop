package com.shopperstop.user_services.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/**").permitAll()// Allow health checks
                        .anyRequest().authenticated()                 // Protect everything else
                )
                .csrf(csrf -> csrf.disable())
                .httpBasic(withDefaults()); // This replaces the deprecated .httpBasic()

        return http.build();
    }
}
