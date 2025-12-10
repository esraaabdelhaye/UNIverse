package com.example.backend.config;

import com.example.backend.Utils.MaterialType;
import com.example.backend.Utils.NotificationType;
import com.example.backend.entity.*;
import com.example.backend.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * For development purpose this class will be used to fill
 * The H2 with mock data for testing, debugging, etc.
 * Creates a complete system with one department and one semester.
 */
@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(
            StudentRepo studentRepo,
            DoctorRepo doctorRepo,
            SupervisorRepo supervisorRepo,
            TeachingAssistantRepo taRepo,
            DepartmentRepo departmentRepo,
            SemesterRepo semesterRepo,
            CourseRepo courseRepo,
            CourseEnrollmentRepo enrollmentRepo,
            AssignmentRepo assignmentRepo,
            AssignmentSubmissionRepo submissionRepo,
            AnnouncementRepo announcementRepo,
            MaterialRepo materialRepo,
            EventRepo eventRepo,
            PostRepo postRepo,
            StudentGroupRepo studentGroupRepo,
            PollRepo pollRepo,
            PollOptionRepo pollOptionRepo,
            NotificationRepo notificationRepo,
            QuestionRepo questionRepo,
            StudentRepresentativeRepo studentRepRepo,
            PasswordEncoder passwordEncoder) {

        return args -> {
            System.out.println("=== Starting Database Initialization ===");

            // ==================== SUPERVISOR ====================
            Supervisor supervisor;
            if (supervisorRepo.findByEmail("supervisor@university.edu").isEmpty()) {
                supervisor = new Supervisor();
                supervisor.setName("Dr. Ahmed Hassan");
                supervisor.setEmail("supervisor@university.edu");
                supervisor.setHashedPassword(passwordEncoder.encode("password123"));
                supervisor.setPhoneNumber("010-1234-5678");
                supervisor.setOfficeLocation("Admin Building, Room 100");
                supervisor.setTitle("Professor");
                supervisor.setExpertise("Computer Science, Software Engineering");
                supervisor.setSupervisorRole("Department Head");
                supervisor.setManagedProgram("Computer Science Program");
                supervisor.setStartDate(LocalDate.of(2020, 9, 1));
                supervisor.setStatus("Active");
                supervisor = supervisorRepo.save(supervisor);
                System.out.println("Inserted supervisor: " + supervisor.getEmail());
            } else {
                supervisor = (Supervisor) supervisorRepo.findByEmail("supervisor@university.edu").get();
            }

            // ==================== DEPARTMENT ====================
            Department department;
            if (departmentRepo.findAll().isEmpty()) {
                department = new Department();
                department.setName("Computer Science Department");
                department.setCoordinator(supervisor);
                department = departmentRepo.save(department);
                
                // Update supervisor with department
                supervisor.setDepartment(department);
                supervisorRepo.save(supervisor);
                
                System.out.println("Inserted department: " + department.getName());
            } else {
                department = departmentRepo.findAll().get(0);
            }

            // ==================== SEMESTER ====================
            Semester semester;
            if (semesterRepo.findAll().isEmpty()) {
                semester = new Semester();
                semester.setName("Fall 2025");
                semester.setStartDate(LocalDate.of(2025, 9, 1));
                semester.setEndDate(LocalDate.of(2025, 12, 31));
                semester.setActive(true);
                semester = semesterRepo.save(semester);
                System.out.println("Inserted semester: " + semester.getName());
            } else {
                semester = semesterRepo.findAll().get(0);
            }

            // ==================== DOCTORS ====================
            Doctor doctor1;
            if (doctorRepo.findByEmail("doctor@university.edu").isEmpty()) {
                doctor1 = new Doctor();
                doctor1.setName("Dr. Sara Mohamed");
                doctor1.setEmail("doctor@university.edu");
                doctor1.setHashedPassword(passwordEncoder.encode("password123"));
                doctor1.setPhoneNumber("010-2345-6789");
                doctor1.setOfficeLocation("Building A, Room 201");
                doctor1.setTitle("Associate Professor");
                doctor1.setExpertise("Data Structures, Algorithms");
                doctor1.setDepartment(department);
                doctor1 = doctorRepo.save(doctor1);
                System.out.println("Inserted doctor: " + doctor1.getEmail());
            } else {
                doctor1 = doctorRepo.findByEmail("doctor@university.edu").get();
            }

            Doctor doctor2;
            if (doctorRepo.findByEmail("doctor2@university.edu").isEmpty()) {
                doctor2 = new Doctor();
                doctor2.setName("Dr. Mohamed Ali");
                doctor2.setEmail("doctor2@university.edu");
                doctor2.setHashedPassword(passwordEncoder.encode("password123"));
                doctor2.setPhoneNumber("010-3456-7890");
                doctor2.setOfficeLocation("Building A, Room 202");
                doctor2.setTitle("Assistant Professor");
                doctor2.setExpertise("Database Systems, Web Development");
                doctor2.setDepartment(department);
                doctor2 = doctorRepo.save(doctor2);
                System.out.println("Inserted doctor: " + doctor2.getEmail());
            } else {
                doctor2 = doctorRepo.findByEmail("doctor2@university.edu").get();
            }

            // ==================== TEACHING ASSISTANTS ====================
            TeachingAssistant ta1;
            if (taRepo.findByEmail("ta@university.edu").isEmpty()) {
                ta1 = new TeachingAssistant();
                ta1.setName("Eng. Fatma Ibrahim");
                ta1.setEmail("ta@university.edu");
                ta1.setHashedPassword(passwordEncoder.encode("password123"));
                ta1.setPhoneNumber("010-4567-8901");
                ta1.setOfficeLocation("Building B, Room 101");
                ta1.setTitle("Teaching Assistant");
                ta1.setExpertise("Programming, Data Structures");
                ta1.setDepartment(department);
                ta1 = taRepo.save(ta1);
                System.out.println("Inserted TA: " + ta1.getEmail());
            } else {
                ta1 = taRepo.findByEmail("ta@university.edu").get();
            }

            TeachingAssistant ta2;
            if (taRepo.findByEmail("ta2@university.edu").isEmpty()) {
                ta2 = new TeachingAssistant();
                ta2.setName("Eng. Omar Khaled");
                ta2.setEmail("ta2@university.edu");
                ta2.setHashedPassword(passwordEncoder.encode("password123"));
                ta2.setPhoneNumber("010-5678-9012");
                ta2.setOfficeLocation("Building B, Room 102");
                ta2.setTitle("Teaching Assistant");
                ta2.setExpertise("Databases, Web Technologies");
                ta2.setDepartment(department);
                ta2 = taRepo.save(ta2);
                System.out.println("Inserted TA: " + ta2.getEmail());
            } else {
                ta2 = taRepo.findByEmail("ta2@university.edu").get();
            }

            // ==================== COURSES ====================
            Course course1;
            if (courseRepo.findByCourseCode("CS101").isEmpty()) {
                course1 = new Course();
                course1.setCourseCode("CS101");
                course1.setName("Introduction to Programming");
                course1.setCredits(3);
                course1.setSemester("Fall 2025");
                course1.setDescription("Fundamentals of programming using Java. Topics include variables, control structures, arrays, and basic OOP concepts.");
                course1.setDepartment(department);
                course1 = courseRepo.save(course1);
                
                // Add doctor to course
                doctor1.addCourse(course1);
                doctorRepo.save(doctor1);
                
                // Add TA to course
                ta1.addCourse(course1);
                taRepo.save(ta1);
                
                // Add course to semester
                semester.addCourse(course1);
                semesterRepo.save(semester);
                
                System.out.println("Inserted course: " + course1.getCourseCode());
            } else {
                course1 = courseRepo.findByCourseCode("CS101").get();
            }

            Course course2;
            if (courseRepo.findByCourseCode("CS201").isEmpty()) {
                course2 = new Course();
                course2.setCourseCode("CS201");
                course2.setName("Data Structures");
                course2.setCredits(3);
                course2.setSemester("Fall 2025");
                course2.setDescription("Study of data structures including linked lists, stacks, queues, trees, and graphs. Algorithm analysis and complexity.");
                course2.setDepartment(department);
                course2 = courseRepo.save(course2);
                
                // Set prerequisite
                course2.addPrerequisite(course1);
                course2 = courseRepo.save(course2);
                
                // Add doctor to course
                doctor1.addCourse(course2);
                doctorRepo.save(doctor1);
                
                // Add TA to course
                ta1.addCourse(course2);
                taRepo.save(ta1);
                
                semester.addCourse(course2);
                semesterRepo.save(semester);
                
                System.out.println("Inserted course: " + course2.getCourseCode());
            } else {
                course2 = courseRepo.findByCourseCode("CS201").get();
            }

            Course course3;
            if (courseRepo.findByCourseCode("CS301").isEmpty()) {
                course3 = new Course();
                course3.setCourseCode("CS301");
                course3.setName("Database Systems");
                course3.setCredits(3);
                course3.setSemester("Fall 2025");
                course3.setDescription("Introduction to database design, SQL, normalization, and transaction management.");
                course3.setDepartment(department);
                course3 = courseRepo.save(course3);
                
                // Add doctor to course
                doctor2.addCourse(course3);
                doctorRepo.save(doctor2);
                
                // Add TA to course
                ta2.addCourse(course3);
                taRepo.save(ta2);
                
                semester.addCourse(course3);
                semesterRepo.save(semester);
                
                System.out.println("Inserted course: " + course3.getCourseCode());
            } else {
                course3 = courseRepo.findByCourseCode("CS301").get();
            }

            // ==================== STUDENTS ====================
            Student student1;
            if (studentRepo.findByEmail("student@university.edu").isEmpty()) {
                student1 = new Student();
                student1.setName("Ali Ahmed");
                student1.setEmail("student@university.edu");
                student1.setHashedPassword(passwordEncoder.encode("password123"));
                student1.setAcademicId(202500001L);
                student1.setDateOfBirth(LocalDate.of(2003, 5, 15));
                student1.setPhoneNumber("010-6789-0123");
                student1.setStatus("Active");
                student1.setDepartment(department);
                student1 = studentRepo.save(student1);
                System.out.println("Inserted student: " + student1.getEmail());
            } else {
                student1 = studentRepo.findByEmail("student@university.edu").get();
            }

            Student student2;
            if (studentRepo.findByEmail("student2@university.edu").isEmpty()) {
                student2 = new Student();
                student2.setName("Nour Mahmoud");
                student2.setEmail("student2@university.edu");
                student2.setHashedPassword(passwordEncoder.encode("password123"));
                student2.setAcademicId(202500002L);
                student2.setDateOfBirth(LocalDate.of(2003, 8, 20));
                student2.setPhoneNumber("010-7890-1234");
                student2.setStatus("Active");
                student2.setDepartment(department);
                student2 = studentRepo.save(student2);
                System.out.println("Inserted student: " + student2.getEmail());
            } else {
                student2 = studentRepo.findByEmail("student2@university.edu").get();
            }

            Student student3;
            if (studentRepo.findByEmail("student3@university.edu").isEmpty()) {
                student3 = new Student();
                student3.setName("Youssef Tarek");
                student3.setEmail("student3@university.edu");
                student3.setHashedPassword(passwordEncoder.encode("password123"));
                student3.setAcademicId(202500003L);
                student3.setDateOfBirth(LocalDate.of(2002, 12, 10));
                student3.setPhoneNumber("010-8901-2345");
                student3.setStatus("Active");
                student3.setDepartment(department);
                student3 = studentRepo.save(student3);
                System.out.println("Inserted student: " + student3.getEmail());
            } else {
                student3 = studentRepo.findByEmail("student3@university.edu").get();
            }

            // ==================== STUDENT REPRESENTATIVE ====================
            StudentRepresentative studentRep;
            if (studentRepRepo.findByEmail("studentrep@university.edu").isEmpty()) {
                studentRep = new StudentRepresentative();
                studentRep.setName("Mariam Hassan");
                studentRep.setEmail("studentrep@university.edu");
                studentRep.setHashedPassword(passwordEncoder.encode("password123"));
                studentRep.setAcademicId(202500004L);
                studentRep.setDateOfBirth(LocalDate.of(2002, 3, 25));
                studentRep.setPhoneNumber("010-9012-3456");
                studentRep.setStatus("Active");
                studentRep.setDepartment(department);
                studentRep.setRole("Class Representative");
                studentRep.setStartDate(LocalDate.of(2025, 9, 1));
                studentRep.setResponsibilities("Coordinate between students and faculty, organize class events");
                studentRep = studentRepRepo.save(studentRep);
                System.out.println("Inserted student representative: " + studentRep.getEmail());
            } else {
                studentRep = studentRepRepo.findByEmail("studentrep@university.edu").get();
            }

            // ==================== COURSE ENROLLMENTS ====================
            if (enrollmentRepo.findAll().isEmpty()) {
                // Enroll student1 in all courses
                CourseEnrollment enrollment1 = new CourseEnrollment();
                enrollment1.setStudent(student1);
                enrollment1.setCourse(course1);
                enrollment1.setEnrollmentDate(LocalDate.of(2025, 9, 1));
                enrollment1.setGrade("A");
                enrollmentRepo.save(enrollment1);

                CourseEnrollment enrollment2 = new CourseEnrollment();
                enrollment2.setStudent(student1);
                enrollment2.setCourse(course2);
                enrollment2.setEnrollmentDate(LocalDate.of(2025, 9, 1));
                enrollmentRepo.save(enrollment2);

                CourseEnrollment enrollment3 = new CourseEnrollment();
                enrollment3.setStudent(student1);
                enrollment3.setCourse(course3);
                enrollment3.setEnrollmentDate(LocalDate.of(2025, 9, 1));
                enrollmentRepo.save(enrollment3);

                // Enroll student2 in courses
                CourseEnrollment enrollment4 = new CourseEnrollment();
                enrollment4.setStudent(student2);
                enrollment4.setCourse(course1);
                enrollment4.setEnrollmentDate(LocalDate.of(2025, 9, 1));
                enrollment4.setGrade("B+");
                enrollmentRepo.save(enrollment4);

                CourseEnrollment enrollment5 = new CourseEnrollment();
                enrollment5.setStudent(student2);
                enrollment5.setCourse(course2);
                enrollment5.setEnrollmentDate(LocalDate.of(2025, 9, 1));
                enrollmentRepo.save(enrollment5);

                // Enroll student3 in courses
                CourseEnrollment enrollment6 = new CourseEnrollment();
                enrollment6.setStudent(student3);
                enrollment6.setCourse(course1);
                enrollment6.setEnrollmentDate(LocalDate.of(2025, 9, 1));
                enrollment6.setGrade("A-");
                enrollmentRepo.save(enrollment6);

                // Enroll student rep in courses
                CourseEnrollment enrollment7 = new CourseEnrollment();
                enrollment7.setStudent(studentRep);
                enrollment7.setCourse(course1);
                enrollment7.setEnrollmentDate(LocalDate.of(2025, 9, 1));
                enrollmentRepo.save(enrollment7);

                CourseEnrollment enrollment8 = new CourseEnrollment();
                enrollment8.setStudent(studentRep);
                enrollment8.setCourse(course2);
                enrollment8.setEnrollmentDate(LocalDate.of(2025, 9, 1));
                enrollmentRepo.save(enrollment8);

                System.out.println("Inserted course enrollments");
            }

            // ==================== ASSIGNMENTS ====================
            Assignment assignment1 = null;
            Assignment assignment2 = null;
            if (assignmentRepo.findAll().isEmpty()) {
                assignment1 = new Assignment();
                assignment1.setTitle("Java Basics Lab");
                assignment1.setDescription("Complete the exercises on variables, loops, and conditionals.");
                assignment1.setDueDate(LocalDateTime.of(2025, 10, 15, 23, 59));
                assignment1.setMaxScore(100);
                assignment1.setCourse(course1);
                assignment1 = assignmentRepo.save(assignment1);

                assignment2 = new Assignment();
                assignment2.setTitle("Linked List Implementation");
                assignment2.setDescription("Implement a doubly linked list with all basic operations.");
                assignment2.setDueDate(LocalDateTime.of(2025, 11, 1, 23, 59));
                assignment2.setMaxScore(100);
                assignment2.setCourse(course2);
                assignment2 = assignmentRepo.save(assignment2);

                Assignment assignment3 = new Assignment();
                assignment3.setTitle("SQL Query Practice");
                assignment3.setDescription("Write SQL queries for the given database schema.");
                assignment3.setDueDate(LocalDateTime.of(2025, 10, 20, 23, 59));
                assignment3.setMaxScore(50);
                assignment3.setCourse(course3);
                assignmentRepo.save(assignment3);

                System.out.println("Inserted assignments");
            } else {
                assignment1 = assignmentRepo.findAll().get(0);
                if (assignmentRepo.findAll().size() > 1) {
                    assignment2 = assignmentRepo.findAll().get(1);
                }
            }

            // ==================== ASSIGNMENT SUBMISSIONS ====================
            if (submissionRepo.findAll().isEmpty() && assignment1 != null) {
                AssignmentSubmission submission1 = new AssignmentSubmission();
                submission1.setStudent(student1);
                submission1.setCourse(course1);
                submission1.setAssignment(assignment1);
                submission1.setSubmissionDate(LocalDate.of(2025, 10, 14));
                submission1.setStatus("graded");
                submission1.setGrade("95");
                submission1.setFeedback("Excellent work! Good understanding of concepts.");
                submission1.setSubmissionFile("/uploads/student1_assignment1.zip");
                submissionRepo.save(submission1);

                AssignmentSubmission submission2 = new AssignmentSubmission();
                submission2.setStudent(student2);
                submission2.setCourse(course1);
                submission2.setAssignment(assignment1);
                submission2.setSubmissionDate(LocalDate.of(2025, 10, 15));
                submission2.setStatus("graded");
                submission2.setGrade("88");
                submission2.setFeedback("Good work, minor improvements needed in loop optimization.");
                submission2.setSubmissionFile("/uploads/student2_assignment1.zip");
                submissionRepo.save(submission2);

                if (assignment2 != null) {
                    AssignmentSubmission submission3 = new AssignmentSubmission();
                    submission3.setStudent(student1);
                    submission3.setCourse(course2);
                    submission3.setAssignment(assignment2);
                    submission3.setSubmissionDate(LocalDate.of(2025, 10, 30));
                    submission3.setStatus("submitted");
                    submission3.setSubmissionFile("/uploads/student1_assignment2.zip");
                    submissionRepo.save(submission3);
                }

                System.out.println("Inserted assignment submissions");
            }

            // ==================== MATERIALS ====================
            if (materialRepo.findAll().isEmpty()) {
                Material material1 = new Material();
                material1.setTitle("Week 1 - Introduction to Java");
                material1.setUrl("/materials/cs101/week1_intro.pdf");
                material1.setUploadDate(LocalDateTime.of(2025, 9, 2, 10, 0));
                material1.setType(MaterialType.PDF);
                material1.setCourse(course1);
                material1.setDoctorUploader(doctor1);
                materialRepo.save(material1);

                Material material2 = new Material();
                material2.setTitle("Week 2 - Variables and Data Types");
                material2.setUrl("/materials/cs101/week2_variables.pdf");
                material2.setUploadDate(LocalDateTime.of(2025, 9, 9, 10, 0));
                material2.setType(MaterialType.PDF);
                material2.setCourse(course1);
                material2.setDoctorUploader(doctor1);
                materialRepo.save(material2);

                Material material3 = new Material();
                material3.setTitle("Lab 1 - Getting Started with Java");
                material3.setUrl("/materials/cs101/lab1_tutorial.mp4");
                material3.setUploadDate(LocalDateTime.of(2025, 9, 5, 14, 0));
                material3.setType(MaterialType.VIDEO);
                material3.setCourse(course1);
                material3.setTaUploader(ta1);
                materialRepo.save(material3);

                Material material4 = new Material();
                material4.setTitle("Data Structures Textbook");
                material4.setUrl("/materials/cs201/textbook.pdf");
                material4.setUploadDate(LocalDateTime.of(2025, 9, 1, 9, 0));
                material4.setType(MaterialType.TEXTBOOK);
                material4.setCourse(course2);
                material4.setDoctorUploader(doctor1);
                materialRepo.save(material4);

                Material material5 = new Material();
                material5.setTitle("SQL Tutorial");
                material5.setUrl("/materials/cs301/sql_tutorial.pdf");
                material5.setUploadDate(LocalDateTime.of(2025, 9, 3, 11, 0));
                material5.setType(MaterialType.PDF);
                material5.setCourse(course3);
                material5.setDoctorUploader(doctor2);
                materialRepo.save(material5);

                System.out.println("Inserted materials");
            }

            // ==================== ANNOUNCEMENTS ====================
            if (announcementRepo.findAll().isEmpty()) {
                Announcement announcement1 = new Announcement();
                announcement1.setTitle("Welcome to CS101!");
                announcement1.setContent("Welcome to Introduction to Programming. Please review the course syllabus and join our online discussion forum.");
                announcement1.setCreatedAt(LocalDateTime.of(2025, 9, 1, 8, 0));
                announcement1.setVisibility("public");
                announcement1.setStatus("active");
                announcement1.setDoctorAuthor(doctor1);
                announcement1.setCourse(course1);
                announcementRepo.save(announcement1);

                Announcement announcement2 = new Announcement();
                announcement2.setTitle("Assignment 1 Due Date Extended");
                announcement2.setContent("Due to popular request, the deadline for Assignment 1 has been extended by 2 days.");
                announcement2.setCreatedAt(LocalDateTime.of(2025, 10, 12, 14, 30));
                announcement2.setVisibility("public");
                announcement2.setStatus("active");
                announcement2.setTaAuthor(ta1);
                announcement2.setCourse(course1);
                announcementRepo.save(announcement2);

                Announcement announcement3 = new Announcement();
                announcement3.setTitle("Department Meeting");
                announcement3.setContent("All CS students are invited to attend the department meeting on October 20th at 2 PM.");
                announcement3.setCreatedAt(LocalDateTime.of(2025, 10, 15, 9, 0));
                announcement3.setVisibility("public");
                announcement3.setStatus("active");
                announcement3.setSupervisorAuthor(supervisor);
                announcementRepo.save(announcement3);

                Announcement announcement4 = new Announcement();
                announcement4.setTitle("Study Group Formation");
                announcement4.setContent("Students interested in forming study groups for midterms, please contact me.");
                announcement4.setCreatedAt(LocalDateTime.of(2025, 10, 10, 16, 0));
                announcement4.setVisibility("public");
                announcement4.setStatus("active");
                announcement4.setStudentRepAuthor(studentRep);
                announcementRepo.save(announcement4);

                System.out.println("Inserted announcements");
            }

            // ==================== EVENTS ====================
            if (eventRepo.findAll().isEmpty()) {
                Event event1 = new Event();
                event1.setTitle("Midterm Exam - CS101");
                event1.setDescription("Midterm examination covering chapters 1-5");
                event1.setStartTime(LocalDateTime.of(2025, 10, 25, 10, 0));
                event1.setEndTime(LocalDateTime.of(2025, 10, 25, 12, 0));
                event1.setLocation("Hall A");
                event1.setDoctor(doctor1);
                eventRepo.save(event1);

                Event event2 = new Event();
                event2.setTitle("Office Hours");
                event2.setDescription("Weekly office hours for CS101 and CS201 students");
                event2.setStartTime(LocalDateTime.of(2025, 10, 16, 14, 0));
                event2.setEndTime(LocalDateTime.of(2025, 10, 16, 16, 0));
                event2.setLocation("Building A, Room 201");
                event2.setDoctor(doctor1);
                eventRepo.save(event2);

                Event event3 = new Event();
                event3.setTitle("Lab Session - Data Structures");
                event3.setDescription("Hands-on practice with linked list implementation");
                event3.setStartTime(LocalDateTime.of(2025, 10, 18, 10, 0));
                event3.setEndTime(LocalDateTime.of(2025, 10, 18, 12, 0));
                event3.setLocation("Lab 1");
                event3.setTa(ta1);
                eventRepo.save(event3);

                Event event4 = new Event();
                event4.setTitle("Department Orientation");
                event4.setDescription("Orientation session for new CS students");
                event4.setStartTime(LocalDateTime.of(2025, 9, 5, 9, 0));
                event4.setEndTime(LocalDateTime.of(2025, 9, 5, 11, 0));
                event4.setLocation("Main Auditorium");
                event4.setSupervisor(supervisor);
                eventRepo.save(event4);

                System.out.println("Inserted events");
            }

            // ==================== POSTS ====================
            if (postRepo.findAll().isEmpty()) {
                Post post1 = new Post();
                post1.setTitle("Tips for Success in Programming");
                post1.setContent("Here are some tips to excel in programming courses: 1. Practice daily 2. Don't be afraid to ask questions 3. Work on projects outside class");
                post1.setCreatedAt(LocalDateTime.of(2025, 9, 10, 15, 0));
                post1.setStatus("public");
                post1 = postRepo.save(post1);
                doctor1.addPost(post1);
                doctorRepo.save(doctor1);

                Post post2 = new Post();
                post2.setTitle("Lab Resources");
                post2.setContent("Additional resources for lab exercises are now available in the materials section.");
                post2.setCreatedAt(LocalDateTime.of(2025, 9, 15, 11, 0));
                post2.setStatus("public");
                post2 = postRepo.save(post2);
                ta1.addPost(post2);
                taRepo.save(ta1);

                Post post3 = new Post();
                post3.setTitle("Class Social Event");
                post3.setContent("Join us for a class social event next Friday! Great opportunity to meet your classmates.");
                post3.setCreatedAt(LocalDateTime.of(2025, 10, 5, 12, 0));
                post3.setStatus("public");
                post3 = postRepo.save(post3);
                studentRep.addPost(post3);
                studentRepRepo.save(studentRep);

                System.out.println("Inserted posts");
            }

            // ==================== STUDENT GROUPS ====================
            if (studentGroupRepo.findAll().isEmpty()) {
                StudentGroup group1 = new StudentGroup();
                group1.setName("CS101 Study Group");
                group1.setActivity("Weekly study sessions for CS101");
                group1 = studentGroupRepo.save(group1);
                group1.addStudent(student1);
                group1.addStudent(student2);
                group1.addStudent(studentRep);
                studentGroupRepo.save(group1);

                StudentGroup group2 = new StudentGroup();
                group2.setName("Programming Club");
                group2.setActivity("Competitive programming and project development");
                group2 = studentGroupRepo.save(group2);
                group2.addStudent(student1);
                group2.addStudent(student3);
                studentGroupRepo.save(group2);

                System.out.println("Inserted student groups");
            }

            // ==================== POLLS ====================
            if (pollRepo.findAll().isEmpty()) {
                Poll poll1 = new Poll();
                poll1.setTitle("Best time for extra office hours?");
                poll1.setStartTime(LocalDateTime.of(2025, 10, 10, 8, 0));
                poll1.setEndTime(LocalDateTime.of(2025, 10, 17, 23, 59));
                poll1.setDoctorCreator(doctor1);
                poll1 = pollRepo.save(poll1);

                PollOption option1 = new PollOption();
                option1.setText("Monday 2-4 PM");
                option1.setPoll(poll1);
                option1.setVoteCount(5);
                pollOptionRepo.save(option1);

                PollOption option2 = new PollOption();
                option2.setText("Wednesday 3-5 PM");
                option2.setPoll(poll1);
                option2.setVoteCount(8);
                pollOptionRepo.save(option2);

                PollOption option3 = new PollOption();
                option3.setText("Friday 1-3 PM");
                option3.setPoll(poll1);
                option3.setVoteCount(3);
                pollOptionRepo.save(option3);

                Poll poll2 = new Poll();
                poll2.setTitle("Preferred format for class notes?");
                poll2.setStartTime(LocalDateTime.of(2025, 10, 1, 8, 0));
                poll2.setEndTime(LocalDateTime.of(2025, 10, 8, 23, 59));
                poll2.setStudentRepresentative(studentRep);
                poll2 = pollRepo.save(poll2);

                PollOption option4 = new PollOption();
                option4.setText("PDF Documents");
                option4.setPoll(poll2);
                option4.setVoteCount(12);
                pollOptionRepo.save(option4);

                PollOption option5 = new PollOption();
                option5.setText("Video Recordings");
                option5.setPoll(poll2);
                option5.setVoteCount(15);
                pollOptionRepo.save(option5);

                System.out.println("Inserted polls");
            }

            // ==================== NOTIFICATIONS ====================
            if (notificationRepo.findAll().isEmpty()) {
                Notification notification1 = new Notification();
                notification1.setTitle("New Assignment Posted");
                notification1.setType(NotificationType.ASSIGNMENT);
                notification1.setMessage("A new assignment 'Java Basics Lab' has been posted for CS101.");
                notification1.setDoctorCreator(doctor1);
                notification1.addStudent(student1);
                notification1.addStudent(student2);
                notification1.addStudent(student3);
                notification1.addStudent(studentRep);
                notificationRepo.save(notification1);

                Notification notification2 = new Notification();
                notification2.setTitle("Upcoming Exam");
                notification2.setType(NotificationType.EXAM);
                notification2.setMessage("Reminder: CS101 Midterm Exam is scheduled for October 25th.");
                notification2.setDoctorCreator(doctor1);
                notification2.addStudent(student1);
                notification2.addStudent(student2);
                notification2.addStudent(studentRep);
                notificationRepo.save(notification2);

                Notification notification3 = new Notification();
                notification3.setTitle("New Poll Available");
                notification3.setType(NotificationType.POLL);
                notification3.setMessage("Please vote on the poll regarding office hours timing.");
                notification3.setTaCreator(ta1);
                notification3.addStudent(student1);
                notification3.addStudent(student2);
                notification3.addStudent(student3);
                notificationRepo.save(notification3);

                System.out.println("Inserted notifications");
            }

            // ==================== QUESTIONS ====================
            if (questionRepo.findAll().isEmpty()) {
                Question question1 = new Question();
                question1.setContent("What is the difference between a while loop and a for loop in Java?");
                question1.setCreatedAt(LocalDateTime.of(2025, 9, 20, 14, 30));
                question1.setAuthor(student1);
                question1.setDoctorResponder(doctor1);
                question1.setAnswer("A for loop is typically used when you know the number of iterations, while a while loop is used when the iteration count depends on a condition.");
                questionRepo.save(question1);

                Question question2 = new Question();
                question2.setContent("How do I implement a recursive function for factorial?");
                question2.setCreatedAt(LocalDateTime.of(2025, 9, 25, 10, 15));
                question2.setAuthor(student2);
                question2.setTaResponder(ta1);
                question2.setAnswer("A factorial function can be implemented recursively by returning n * factorial(n-1) with base case factorial(0) = 1.");
                questionRepo.save(question2);

                Question question3 = new Question();
                question3.setContent("What are the advantages of using a linked list over an array?");
                question3.setCreatedAt(LocalDateTime.of(2025, 10, 5, 16, 45));
                question3.setAuthor(student3);
                // No answer yet - pending question
                questionRepo.save(question3);

                System.out.println("Inserted questions");
            }

            System.out.println("=== Database Initialization Complete ===");
            System.out.println("\n=== TEST ACCOUNTS ===");
            System.out.println("Supervisor: supervisor@university.edu / password123");
            System.out.println("Doctor 1: doctor@university.edu / password123");
            System.out.println("Doctor 2: doctor2@university.edu / password123");
            System.out.println("TA 1: ta@university.edu / password123");
            System.out.println("TA 2: ta2@university.edu / password123");
            System.out.println("Student 1: student@university.edu / password123");
            System.out.println("Student 2: student2@university.edu / password123");
            System.out.println("Student 3: student3@university.edu / password123");
            System.out.println("Student Rep: studentrep@university.edu / password123");
        };
    }
}
