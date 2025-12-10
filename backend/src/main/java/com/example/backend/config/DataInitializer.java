package com.example.backend.config;

import com.example.backend.entity.Student;
import com.example.backend.repository.StudentRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * For development purpose this class will be used to fill
 * The H2 with mock data for testing , debugging , etc
 */
@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(StudentRepo studentRepo, PasswordEncoder passwordEncoder) {
        return args -> {
            if (studentRepo.findByEmail("student@example.com").isEmpty()) {

                Student student = new Student();
                student.setName("Test Student");
                student.setEmail("student@example.com");
                // Hash the password before saving
                student.setHashedPassword(passwordEncoder.encode("password123"));

                studentRepo.save(student);

                System.out.println("Inserted test student: " + student.getEmail());
            }
        };
    }
}

