package com.example.backend.dto;

import com.example.backend.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseEnrollmentDTO {

    private String grade;     // new
    private CourseDTO course;
    private StudentDTO student;  // new
    private Date enrollmentDate;
    private String professorName;
    private Integer enrolledStudents;
    private Integer totalCapacity;
    private String status;
}
