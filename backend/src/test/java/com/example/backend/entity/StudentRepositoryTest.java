package com.example.backend.entity;

import com.example.backend.repository.DepartmentRepo;
import com.example.backend.repository.SupervisorRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.example.backend.repository.StudentRepo;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class StudentRepositoryTest {

    @Autowired
    private StudentRepo studentRepository;

    @Autowired
    private DepartmentRepo departmentRepository;

    @Autowired
    private SupervisorRepo supervisorRepository;

    @Test
    void testSaveStudent() {
        Department dept = new Department();
        Supervisor coordinator = new Supervisor();
        coordinator.setName("John Doe");
        coordinator.setEmail("dr.smith@example.com");
        supervisorRepository.save(coordinator);
        dept.setCoordinator(coordinator);
        dept.setName("CS");
        departmentRepository.save(dept);

        Student student = new Student();
        student.setName("Alice");
        student.setEmail("alice@example.com");
        student.setAcademicId(12345L);
        student.setDepartment(dept);

        studentRepository.save(student);

        assertThat(student.getId()).isNotNull();
        assertThat(studentRepository.findById(student.getId())).isPresent();
    }

    @Test
    void testAddEnrollmentAndSubmission() {
        Student student = new Student();
        student.setName("Bob");
        student.setEmail("bob@example.com");
        student.setAcademicId(54321L);
        studentRepository.save(student);

        assertThat(student.getEnrollments()).isEmpty();
        assertThat(student.getSubmissions()).isEmpty();
    }
}
