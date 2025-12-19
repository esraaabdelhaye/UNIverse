package com.example.backend.dto;

import com.example.backend.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseEnrollmentDTO {
    private long courseId;         // new: to get all course enrollments from course then get grades
    private String grade;     // new
    private CourseDTO course;
    private String professorName;
    private Integer enrolledStudents;
    private Integer totalCapacity;
    private String status;
}
