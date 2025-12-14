//package com.example.backend.config;
//
//import com.example.backend.entity.*;
//import com.example.backend.repository.*;
//import com.example.backend.Utils.MaterialType;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Component
//public class DataLoader implements CommandLineRunner {
//
//    private final DepartmentRepo departmentRepo;
//    private final DoctorRepo doctorRepo;
//    private final SupervisorRepo supervisorRepo;
//    private final StudentRepo studentRepo;
//    private final StudentRepresentativeRepo studentRepRepo;
//    private final TeachingAssistantRepo taRepo;
//    private final CourseRepo courseRepo;
//    private final CourseEnrollmentRepo enrollmentRepo;
//    private final AssignmentRepo assignmentRepo;
//    private final MaterialRepo materialRepo;
//    private final AnnouncementRepo announcementRepo;
//    private final EventRepo eventRepo;
//    private final PasswordEncoder passwordEncoder;
//
//    public DataLoader(DepartmentRepo departmentRepo, DoctorRepo doctorRepo,
//                     SupervisorRepo supervisorRepo, StudentRepo studentRepo,
//                     StudentRepresentativeRepo studentRepRepo, TeachingAssistantRepo taRepo,
//                     CourseRepo courseRepo, CourseEnrollmentRepo enrollmentRepo,
//                     AssignmentRepo assignmentRepo, MaterialRepo materialRepo,
//                     AnnouncementRepo announcementRepo, EventRepo eventRepo,
//                     PasswordEncoder passwordEncoder) {
//        this.departmentRepo = departmentRepo;
//        this.doctorRepo = doctorRepo;
//        this.supervisorRepo = supervisorRepo;
//        this.studentRepo = studentRepo;
//        this.studentRepRepo = studentRepRepo;
//        this.taRepo = taRepo;
//        this.courseRepo = courseRepo;
//        this.enrollmentRepo = enrollmentRepo;
//        this.assignmentRepo = assignmentRepo;
//        this.materialRepo = materialRepo;
//        this.announcementRepo = announcementRepo;
//        this.eventRepo = eventRepo;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        // Check if data already exists
//        if (departmentRepo.count() > 0) {
//            return; // Data already exists, skip loading
//        }
//
//        // Create Supervisors first (for department coordinator)
//        Supervisor supervisor1 = new Supervisor();
//        supervisor1.setName("Dr. Ahmed Hassan");
//        supervisor1.setEmail("supervisor@test.com");
//        supervisor1.setPhoneNumber("201001234567");
//        supervisor1.setOfficeLocation("CS Building, Room 101");
//        supervisor1.setTitle("Professor");
//        supervisor1.setExpertise("Artificial Intelligence");
//        supervisor1.setSupervisorRole("Department Coordinator");
//        supervisor1.setManagedProgram("Computer Science");
//        supervisor1.setStartDate(LocalDate.of(2023, 1, 1));
//        supervisor1.setStatus("Active");
//        supervisor1.setHashedPassword(passwordEncoder.encode("12345678"));
//
//        Supervisor supervisor2 = new Supervisor();
//        supervisor2.setName("Dr. Fatima Mohamed");
//        supervisor2.setEmail("fatima.mohamed@university.edu");
//        supervisor2.setPhoneNumber("201002234567");
//        supervisor2.setOfficeLocation("ENG Building, Room 205");
//        supervisor2.setTitle("Associate Professor");
//        supervisor2.setExpertise("Mechanical Engineering");
//        supervisor2.setSupervisorRole("Department Head");
//        supervisor2.setManagedProgram("Engineering");
//        supervisor2.setStartDate(LocalDate.of(2020, 6, 1));
//        supervisor2.setStatus("Active");
//        supervisor2.setHashedPassword(passwordEncoder.encode("12345678"));
//
//
//        // Save Supervisors first (without department) to generate IDs
//        supervisorRepo.save(supervisor1);
//        supervisorRepo.save(supervisor2);
//
//        // Create Departments
//        Department dept1 = new Department();
//        dept1.setName("Computer Science");
//        dept1.setCoordinator(supervisor1);
//
//        Department dept2 = new Department();
//        dept2.setName("Engineering");
//        dept2.setCoordinator(supervisor2);
//
//        Department dept3 = new Department();
//        dept3.setName("Business");
//        // dept3 has no coordinator for now - handle if nullable, otherwise this might fail if coordinator is required.
//        // Assuming Department.coordinator is nullable for now or we need a dummy supervisor.
//        // But based on error, it seems non-nullable.
//        // Let's check Department entity. If it is non-nullable, dept3 will fail.
//        // For now, let's comment out dept3 or give it a coordinator if needed.
//        // But wait, the previous code didn't set coordinator for dept3.
//        // Let's check if Department.coordinator is nullable.
//        // The error said "Non-nullable association(s): ([com.example.backend.entity.Department.coordinator])"
//        // So dept3 WILL fail if we save it without coordinator.
//        // Let's create a dummy supervisor for dept3 or skip saving it if not needed.
//        // Or maybe the previous code was failing on dept1/dept2.
//
//        // Let's stick to fixing the order for dept1 and dept2 first.
//
//        departmentRepo.save(dept1);
//        departmentRepo.save(dept2);
//        // departmentRepo.save(dept3); // Commenting out dept3 to avoid error if coordinator is required
//
//        // Set department for supervisors and update
//        supervisor1.setDepartment(dept1);
//        supervisor2.setDepartment(dept2);
//
//        supervisorRepo.save(supervisor1);
//        supervisorRepo.save(supervisor2);
//
//        // Create Doctors (regular faculty)
//        Doctor doctor1 = new Doctor();
//        doctor1.setName("Dr. Omar Ali");
//        doctor1.setEmail("omar.ali@university.edu");
//        doctor1.setDepartment(dept1);
//        doctor1.setPhoneNumber("201003234567");
//        doctor1.setOfficeLocation("CS Building, Room 102");
//        doctor1.setTitle("Assistant Professor");
//        doctor1.setExpertise("Database Systems");
//        doctor1.setHashedPassword(passwordEncoder.encode("12345678"));
//
//        Doctor doctor2 = new Doctor();
//        doctor2.setName("Dr. Noor Ibrahim");
//        doctor2.setEmail("noor.ibrahim@university.edu");
//        doctor2.setDepartment(dept1);
//        doctor2.setPhoneNumber("201004234567");
//        doctor2.setOfficeLocation("CS Building, Room 103");
//        doctor2.setTitle("Lecturer");
//        doctor2.setExpertise("Web Development");
//        doctor2.setHashedPassword(passwordEncoder.encode("12345678"));
//
//        doctorRepo.save(doctor1);
//        doctorRepo.save(doctor2);
//
//        // Create Teaching Assistants
//        TeachingAssistant ta1 = new TeachingAssistant();
//        ta1.setName("Amira Saleh");
//        ta1.setEmail("amira.saleh@university.edu");
//        ta1.setDepartment(dept1);
//        ta1.setPhoneNumber("201005234567");
//        ta1.setTitle("Teaching Assistant");
//        ta1.setExpertise("Programming");
//        ta1.setHashedPassword(passwordEncoder.encode("12345678"));
//
//        TeachingAssistant ta2 = new TeachingAssistant();
//        ta2.setName("Karim Mansour");
//        ta2.setEmail("karim.mansour@university.edu");
//        ta2.setDepartment(dept1);
//        ta2.setPhoneNumber("201006234567");
//        ta2.setTitle("Teaching Assistant");
//        ta2.setExpertise("Data Structures");
//        ta2.setHashedPassword(passwordEncoder.encode("12345678"));
//
//        taRepo.save(ta1);
//        taRepo.save(ta2);
//
//        // Create Students
//        Student student1 = new Student();
//        student1.setName("Ahmed Mohammed");
//        student1.setEmail("ahmed.m@student.edu");
//        student1.setAcademicId(101L);
//        student1.setDepartment(dept1);
//        student1.setHashedPassword(passwordEncoder.encode("12345678"));
//
//        Student student2 = new Student();
//        student2.setName("Fatima Ali");
//        student2.setEmail("fatima.a@student.edu");
//        student2.setAcademicId(102L);
//        student2.setDepartment(dept1);
//        student2.setHashedPassword(passwordEncoder.encode("12345678"));
//
//        Student student3 = new Student();
//        student3.setName("Sara Ibrahim");
//        student3.setEmail("sara.i@student.edu");
//        student3.setAcademicId(103L);
//        student3.setDepartment(dept1);
//        student3.setHashedPassword(passwordEncoder.encode("12345678"));
//
//        studentRepo.save(student1);
//        studentRepo.save(student2);
//        studentRepo.save(student3);
//
//        // Create Student Representatives
//        StudentRepresentative rep1 = new StudentRepresentative();
//        rep1.setName("Mariam Khalil");
//        rep1.setEmail("mariam.khalil@student.edu");
//        rep1.setAcademicId(201L);
//        rep1.setDepartment(dept1);
//        rep1.setRole("Class Representative");
//        rep1.setStartDate(LocalDate.of(2024, 1, 1));
//        rep1.setEndDate(LocalDate.of(2025, 12, 31));
//        rep1.setStatus("Active");
//        rep1.setHashedPassword(passwordEncoder.encode("12345678"));
//
//        StudentRepresentative rep2 = new StudentRepresentative();
//        rep2.setName("Hassan Nour");
//        rep2.setEmail("hassan.nour@student.edu");
//        rep2.setAcademicId(202L);
//        rep2.setDepartment(dept1);
//        rep2.setRole("Department Representative");
//        rep2.setStartDate(LocalDate.of(2024, 1, 1));
//        rep2.setEndDate(LocalDate.of(2025, 12, 31));
//        rep2.setStatus("Active");
//        rep2.setHashedPassword(passwordEncoder.encode("12345678"));
//
//        studentRepRepo.save(rep1);
//        studentRepRepo.save(rep2);
//
//        // Create Courses
//        Course course1 = new Course();
//        course1.setName("Programming I");
//        course1.setCourseCode("CS101");
//        course1.setCredits(3);
//        course1.setDepartment(dept1);
//        course1.setSemester("Fall 2024");
//        course1.setDescription("Introduction to Programming");
//
//        Course course2 = new Course();
//        course2.setName("Data Structures");
//        course2.setCourseCode("CS201");
//        course2.setCredits(3);
//        course2.setDepartment(dept1);
//        course2.setSemester("Spring 2024");
//        course2.setDescription("Data Structures and Algorithms");
//
//        Course course3 = new Course();
//        course3.setName("Mechanics I");
//        course3.setCourseCode("ENG101");
//        course3.setCredits(4);
//        course3.setDepartment(dept2);
//        course3.setSemester("Fall 2024");
//        course3.setDescription("Classical Mechanics");
//
//        courseRepo.save(course1);
//        courseRepo.save(course2);
//        courseRepo.save(course3);
//
//        // Create Course Enrollments
//        CourseEnrollment enrollment1 = new CourseEnrollment();
//        enrollment1.setStudent(student1);
//        enrollment1.setCourse(course1);
//        enrollment1.setEnrollmentDate(LocalDate.of(2024, 9, 1));
//        enrollment1.setGrade("A");
//
//        CourseEnrollment enrollment2 = new CourseEnrollment();
//        enrollment2.setStudent(student2);
//        enrollment2.setCourse(course1);
//        enrollment2.setEnrollmentDate(LocalDate.of(2024, 9, 1));
//        enrollment2.setGrade("B+");
//
//        CourseEnrollment enrollment3 = new CourseEnrollment();
//        enrollment3.setStudent(student3);
//        enrollment3.setCourse(course2);
//        enrollment3.setEnrollmentDate(LocalDate.of(2024, 9, 1));
//        enrollment3.setGrade("A-");
//
//        enrollmentRepo.save(enrollment1);
//        enrollmentRepo.save(enrollment2);
//        enrollmentRepo.save(enrollment3);
//
//        // Create Assignments
//        Assignment assignment1 = new Assignment();
//        assignment1.setTitle("Programming Assignment 1");
//        assignment1.setDescription("Write a simple calculator program");
//        assignment1.setDueDate(LocalDateTime.of(2024, 10, 15, 23, 59));
//        assignment1.setCourse(course1);
//
//        Assignment assignment2 = new Assignment();
//        assignment2.setTitle("Data Structures Project");
//        assignment2.setDescription("Implement a linked list");
//        assignment2.setDueDate(LocalDateTime.of(2024, 11, 20, 23, 59));
//        assignment2.setCourse(course2);
//
//        assignmentRepo.save(assignment1);
//        assignmentRepo.save(assignment2);
//
//        // Create Materials
//        Material material1 = new Material();
//        material1.setTitle("Lecture 1 - Introduction");
//        material1.setUrl("https://university.edu/materials/lecture1.pdf");
//        material1.setUploadDate(LocalDateTime.of(2024, 9, 1, 9, 0));
//        material1.setType(MaterialType.PDF);
//        material1.setCourse(course1);
//        material1.setDoctorUploader(doctor1);
//
//        Material material2 = new Material();
//        material2.setTitle("Lecture 2 - Variables");
//        material2.setUrl("https://university.edu/materials/lecture2.pdf");
//        material2.setUploadDate(LocalDateTime.of(2024, 9, 8, 10, 0));
//        material2.setType(MaterialType.PDF);
//        material2.setCourse(course1);
//        material2.setDoctorUploader(doctor1);
//
//        materialRepo.save(material1);
//        materialRepo.save(material2);
//
//        // Create Announcements
//        Announcement announcement1 = new Announcement();
//        announcement1.setTitle("Welcome to Course");
//        announcement1.setContent("Welcome all students to CS101");
//        announcement1.setCreatedAt(LocalDateTime.of(2024, 9, 1, 8, 0));
//        announcement1.setUpdatedAt(LocalDateTime.of(2024, 9, 1, 8, 0));
//        announcement1.setDoctorAuthor(doctor1);
//
//        Announcement announcement2 = new Announcement();
//        announcement2.setTitle("Exam Schedule");
//        announcement2.setContent("Final exam on 2024-12-20");
//        announcement2.setCreatedAt(LocalDateTime.of(2024, 10, 1, 9, 0));
//        announcement2.setUpdatedAt(LocalDateTime.of(2024, 10, 1, 9, 0));
//        announcement2.setDoctorAuthor(doctor1);
//
//        announcementRepo.save(announcement1);
//        announcementRepo.save(announcement2);
//
//        // Create Events
//        Event event1 = new Event();
//        event1.setTitle("Programming Workshop");
//        event1.setDescription("Advanced programming techniques");
//        event1.setStartTime(LocalDateTime.of(2024, 11, 5, 14, 0));
//        event1.setEndTime(LocalDateTime.of(2024, 11, 5, 16, 0));
//        event1.setLocation("CS Building, Auditorium");
//        event1.setSupervisor(supervisor1);
//
//        Event event2 = new Event();
//        event2.setTitle("Data Structures Seminar");
//        event2.setDescription("Tree structures and algorithms");
//        event2.setStartTime(LocalDateTime.of(2024, 11, 12, 15, 0));
//        event2.setEndTime(LocalDateTime.of(2024, 11, 12, 17, 0));
//        event2.setLocation("CS Building, Room 200");
//        event2.setDoctor(doctor2);
//
//        eventRepo.save(event1);
//        eventRepo.save(event2);
//    }
//}
