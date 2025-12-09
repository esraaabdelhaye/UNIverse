import { Component, OnInit } from '@angular/core';

interface Submission {
  id: number;
  studentName: string;
  submissionDate: Date;
  status: 'submitted' | 'late' | 'missing';
  fileName: string;
  grade?: number;
}

@Component({
  selector: 'app-ta-submissions',
  templateUrl: './ta-submissions.component.html',
  styleUrls: ['./ta-submissions.component.css']
})
export class TaSubmissionsComponent implements OnInit {
  selectedCourse: string = 'CS101';
  selectedAssignment: string = 'Problem Set 5';
  selectedStatus: string = 'all';

  courses = ['CS101', 'MATH201', 'PHYS150'];
  assignmentsByCourse: { [key: string]: string[] } = {
    'CS101': ['Problem Set 1', 'Problem Set 2', 'Problem Set 5', 'Midterm Exam'],
    'MATH201': ['Quiz 1', 'Quiz 2', 'Midterm Exam', 'Final Exam'],
    'PHYS150': ['Lab Report 1', 'Lab Report 2', 'Midterm', 'Final Project']
  };

  submissions: Submission[] = [
    { id: 1, studentName: 'Alice Johnson', submissionDate: new Date(2024, 9, 26, 10, 30), status: 'submitted', fileName: 'ProblemSet5_Alice.pdf', grade: 92 },
    { id: 2, studentName: 'Bob Smith', submissionDate: new Date(2024, 9, 27, 14, 15), status: 'submitted', fileName: 'ProblemSet5_Bob.pdf' },
    { id: 3, studentName: 'Charlie Davis', submissionDate: new Date(2024, 9, 29, 23, 45), status: 'late', fileName: 'ProblemSet5_Charlie.pdf' },
    { id: 4, studentName: 'Diana Miller', submissionDate: null, status: 'missing', fileName: null },
    { id: 5, studentName: 'Eve Martinez', submissionDate: new Date(2024, 9, 25, 16, 20), status: 'submitted', fileName: 'ProblemSet5_Eve.pdf', grade: 88 },
  ];

  filteredSubmissions: Submission[] = [];

  ngOnInit() {
    this.filterSubmissions();
  }

  getAssignments(): string[] {
    return this.assignmentsByCourse[this.selectedCourse] || [];
  }

  filterSubmissions() {
    this.filteredSubmissions = this.submissions.filter(s =>
      this.selectedStatus === 'all' || s.status === this.selectedStatus
    );
  }

  downloadSubmission(submission: Submission) {
    console.log('Download:', submission.fileName);
  }

  gradeSubmission(submission: Submission) {
    console.log('Grade:', submission);
  }

  getStatusColor(status: string): string {
    const colors: { [key: string]: string } = {
      'submitted': '#4CAF50',
      'late': '#FF9800',
      'missing': '#F44336'
    };
    return colors[status] || '#BDBDBD';
  }

  getTotalStats() {
    return {
      total: this.submissions.length,
      submitted: this.submissions.filter(s => s.status === 'submitted').length,
      late: this.submissions.filter(s => s.status === 'late').length,
      missing: this.submissions.filter(s => s.status === 'missing').length
    };
  }
}
