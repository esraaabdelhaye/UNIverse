import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

interface StudentSubmission {
  id: number;
  studentName: string;
  studentEmail: string;
  submissionDate: Date | null;
  status: 'submitted' | 'late' | 'missing';
  fileName: string | null;
  grade?: number;
  feedback?: string;
}

@Component({
  selector: 'app-professor-grade',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './professor-grade.html',
  styleUrls: ['./professor-grade.css']
})
export class ProfessorGradeComponent implements OnInit {
  selectedCourse: string = 'CS101';
  selectedAssignment: string = 'Problem Set 5';
  submissions: StudentSubmission[] = [];
  filteredSubmissions: StudentSubmission[] = [];

  courses = ['CS101', 'CS201', 'CS301', 'CS401'];
  assignmentsByCourse: { [key: string]: string[] } = {
    'CS101': ['Problem Set 1', 'Problem Set 2', 'Problem Set 5', 'Midterm Exam'],
    'CS201': ['Lab 1', 'Lab 2', 'Midterm Exam', 'Final Project'],
    'CS301': ['Algorithm Design', 'Complexity Analysis', 'Final Paper'],
    'CS401': ['Research Project', 'Presentation', 'Final Exam']
  };

  ngOnInit() {
    this.loadSubmissions();
  }

  loadSubmissions() {
    this.submissions = [
      {
        id: 1,
        studentName: 'Alice Johnson',
        studentEmail: 'alice@university.edu',
        submissionDate: new Date(2024, 9, 26, 10, 30),
        status: 'submitted',
        fileName: 'ProblemSet5_Alice.pdf'
      },
      {
        id: 2,
        studentName: 'Bob Williams',
        studentEmail: 'bob@university.edu',
        submissionDate: new Date(2024, 9, 27, 14, 15),
        status: 'submitted',
        fileName: 'ProblemSet5_Bob.pdf'
      },
      {
        id: 3,
        studentName: 'Charlie Davis',
        studentEmail: 'charlie@university.edu',
        submissionDate: new Date(2024, 9, 28, 23, 45),
        status: 'late',
        fileName: 'ProblemSet5_Charlie.pdf'
      },
      {
        id: 4,
        studentName: 'Diana Miller',
        studentEmail: 'diana@university.edu',
        submissionDate: null,
        status: 'missing',
        fileName: null
      },
      {
        id: 5,
        studentName: 'Eve Martinez',
        studentEmail: 'eve@university.edu',
        submissionDate: new Date(2024, 9, 25, 16, 20),
        status: 'submitted',
        fileName: 'ProblemSet5_Eve.pdf',
        grade: 95,
        feedback: 'Excellent work! Very clear explanations.'
      }
    ];
    this.filteredSubmissions = [...this.submissions];
  }

  getAssignments(): string[] {
    return this.assignmentsByCourse[this.selectedCourse as keyof typeof this.assignmentsByCourse] || [];
  }

  onCourseChange() {
    this.selectedAssignment = this.getAssignments()[0] || '';
  }

  onSubmissionGradeChange(submission: StudentSubmission, grade: number) {
    submission.grade = grade;
  }

  onSubmissionFeedbackChange(submission: StudentSubmission, feedback: string) {
    submission.feedback = feedback;
  }

  submitGrades() {
    const gradedSubmissions = this.filteredSubmissions.filter(s => s.grade !== undefined);
    console.log('Submitting grades:', gradedSubmissions);
    alert(`Successfully submitted ${gradedSubmissions.length} grades!`);
  }

  getStatusClass(status: string): string {
    const classes: { [key: string]: string } = {
      'submitted': 'status-success',
      'late': 'status-warning',
      'missing': 'status-error'
    };
    return classes[status] || '';
  }

  downloadSubmission(submission: StudentSubmission) {
    console.log('Downloading:', submission.fileName);
  }
}
