import { Component, OnInit } from '@angular/core';

interface SubmissionItem {
  id: number;
  course: string;
  assignment: string;
  dueDate: Date;
  fileName?: string;
  submittedDate?: Date;
  grade?: number;
  feedback?: string;
}

@Component({
  selector: 'app-student-submit',
  templateUrl: './student-submit.component.html',
  styleUrls: ['./student-submit.component.css']
})
export class StudentSubmitComponent implements OnInit {
  selectedCourse: string = 'PHIL-301';
  selectedAssignment: string = '';
  selectedFile: File | null = null;
  assignmentDetails: any = null;
  previousSubmissions: SubmissionItem[] = [
    {
      id: 1,
      course: 'PHIL-301',
      assignment: 'Essay 1',
      dueDate: new Date(2024, 9, 15),
      fileName: 'Essay1_Final.pdf',
      submittedDate: new Date(2024, 9, 14),
      grade: 92,
      feedback: 'Excellent work!'
    }
  ];

  courses = ['PHIL-301', 'MATH-201', 'CHEM-101'];
  assignments = {
    'PHIL-301': ['Essay 2: The Absurd Hero', 'Final Paper'],
    'MATH-201': ['Problem Set 4', 'Exam'],
    'CHEM-101': ['Lab Report', 'Quiz']
  };

  ngOnInit() {
    this.loadAssignmentDetails();
  }

  loadAssignmentDetails() {
    if (this.selectedCourse && this.selectedAssignment) {
      this.assignmentDetails = {
        title: this.selectedAssignment,
        description: 'Write a comprehensive analysis of the philosophical concepts discussed in class.',
        dueDate: new Date(2024, 9, 28, 23, 59),
        points: 100
      };
    }
  }

  onCourseChange() {
    this.selectedAssignment = '';
    this.assignmentDetails = null;
  }

  onAssignmentChange() {
    this.loadAssignmentDetails();
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  onSubmit() {
    if (!this.selectedFile) {
      alert('Please select a file');
      return;
    }
    alert('Assignment submitted successfully!');
    this.selectedFile = null;
  }

  getAssignments(): string[] {
    return this.assignments[this.selectedCourse] || [];
  }
}

