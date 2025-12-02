package com.example.backend.entity;
import com.example.backend.repository.CourseRepo;
import com.example.backend.repository.DepartmentRepo;
import com.example.backend.repository.SupervisorRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CourseRepositoryTest {

    @Autowired
    private CourseRepo courseRepository;

    @Autowired
    private DepartmentRepo departmentRepository;

    @Autowired
    private SupervisorRepo supervisorRepository;

    @Test
    void testSaveCourse() {
        Department dept = new Department();
        dept.setName("Math");
        Supervisor coordinator = new Supervisor();
        coordinator.setName("John Doe");
        coordinator.setEmail("dr.smith@example.com");
        supervisorRepository.save(coordinator);
        dept.setCoordinator(coordinator);
        departmentRepository.save(dept);

        Course course = new Course();
        course.setCourseCode("MATH101");
        course.setName("Calculus I");
        course.setCredits(3);
        course.setDepartment(dept);

        courseRepository.save(course);

        assertThat(course.getId()).isNotNull();
        assertThat(courseRepository.findById(course.getId())).isPresent();
    }

    @Test
    void testAddPrerequisite() {
        Department dept = new Department();
        dept.setName("Math");
        Supervisor coordinator = new Supervisor();
        coordinator.setName("John Doe");
        coordinator.setEmail("dr.smith@example.com");
        supervisorRepository.save(coordinator);

        dept.setCoordinator(coordinator);
        departmentRepository.save(dept);
        Course c1 = new Course();
        c1.setCourseCode("CS101");
        c1.setName("Intro CS");
        c1.setCredits(3);
        c1.setDepartment(dept);
        courseRepository.save(c1);

        Course c2 = new Course();
        c2.setCourseCode("CS102");
        c2.setName("Data Structures");
        c2.setCredits(3);
        c2.setDepartment(dept);
        courseRepository.save(c2);

        c2.addPrerequisite(c1);
        courseRepository.save(c2);

        assertThat(c2.getPrerequisites()).contains(c1);
        assertThat(c1.getDependentCourses()).contains(c2);
    }
}
