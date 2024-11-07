package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


// GET - anyone
// DELETE & CREATE - Admin
// PATCH (edit) - User & Admin
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> {
                    // Everyone can get
                    auth.requestMatchers(HttpMethod.GET, "/api/*/**").permitAll()
                            // office staff can read and write to tenants. Managers can do everything
                            .requestMatchers(HttpMethod.DELETE, "/api/tenants/*").hasAnyRole("MNGR", "STAFF")
                            .requestMatchers(HttpMethod.POST, "/api/tenants/*").hasAnyRole("MNGR", "STAFF")
                            .requestMatchers(HttpMethod.PATCH, "/api/tenants/*").hasAnyRole("MNGR", "STAFF")
                            .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("MNGR")
                            .requestMatchers(HttpMethod.POST, "/api/**").hasRole("MNGR")
                            .requestMatchers(HttpMethod.PATCH, "/api/**").hasRole("MNGR")
                            .requestMatchers("/graphql").permitAll()
                            .anyRequest().authenticated();
                })
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .build();
    }
}
