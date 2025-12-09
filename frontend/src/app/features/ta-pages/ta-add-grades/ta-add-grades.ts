import { Component, OnInit } from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';

interface StudentGrade {
  id: number;
  studentName: string;
  studentId: string;
  currentGrade?: number;
  feedback?: string;
}

@Component({
  selector: 'app-ta-add-grades',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './ta-add-grades.html',
  styleUrls: ['./ta-add-grades.css']
})
export class TaAddGradesComponent implements OnInit {
  selectedCourse: string = 'CS101';
  selectedAssignment: string = 'Problem Set 5';

  courses = ['CS101', 'MATH201', 'PHYS150'];
  assignmentsByCourse: { [key: string]: string[] } = {
    'CS101': ['Problem Set 1', 'Problem Set 2', 'Problem Set 5', 'Midterm Exam'],
    'MATH201': ['Quiz 1', 'Quiz 2', 'Midterm Exam', 'Final Exam'],
    'PHYS150': ['Lab Report 1', 'Lab Report 2', 'Midterm', 'Final Project']
  };

  students: StudentGrade[] = [
    { id: 1, studentName: 'Alice Johnson', studentId: 'A001', currentGrade: 92 },
    { id: 2, studentName: 'Bob Smith', studentId: 'B002', currentGrade: 85 },
    { id: 3, studentName: 'Charlie Davis', studentId: 'C003' },
    { id: 4, studentName: 'Diana Miller', studentId: 'D004', currentGrade: 78 },
    { id: 5, studentName: 'Eve Martinez', studentId: 'E005' },
  ];

  ngOnInit() {}

  getAssignments(): string[] {
    return this.assignmentsByCourse[this.selectedCourse] || [];
  }

  submitGrades() {
    const gradedStudents = this.students.filter(s => s.currentGrade !== undefined);
    if (gradedStudents.length === 0) {
      alert('Please enter at least one grade');
      return;
    }
    console.log('Submitting grades:', gradedStudents);
    alert(`Successfully submitted ${gradedStudents.length} grades!`);
  }

  saveDraft() {
    alert('Grades saved as draft');
  }
}
