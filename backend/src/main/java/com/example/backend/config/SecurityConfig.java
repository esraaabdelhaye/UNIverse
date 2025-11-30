package com.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
}
