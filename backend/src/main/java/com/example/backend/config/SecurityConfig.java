package com.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Provides a PasswordEncoder bean for hashing passwords.
 *
 * BCrypt is a secure one-way hashing algorithm that:
 *  - automatically adds a random salt to each password
 *  - produces different hashes even for the same password
 *  - cannot be reversed (one-way)
 *
 * Use this bean to:
 *  passwordEncoder.encode(rawPassword);   // hash before saving
 *  passwordEncoder.matches(raw, stored);  // check during login
 */
@Configuration
public class SecurityConfig {

    /**
     * Creates a BCryptPasswordEncoder bean that Spring will manage.
     * Any class can inject this bean to encode or verify passwords.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login").permitAll()
                        .anyRequest().authenticated()
                )
                // Add this line to allow Insomnia to use "Basic Auth"
                .httpBasic(org.springframework.security.config.Customizer.withDefaults())

                .sessionManagement(sm -> sm
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                .cors(org.springframework.security.config.Customizer.withDefaults()); // Use defaults or your bean

        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        // allow Angular and Insomnia
                        .allowedOrigins("http://localhost:4200", "http://localhost:9000")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowCredentials(true)
                        .allowedHeaders("*");
            }
        };
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

}
