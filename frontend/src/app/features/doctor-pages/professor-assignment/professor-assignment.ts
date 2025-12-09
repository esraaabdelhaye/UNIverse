import { Component, OnInit } from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {MatIconModule} from '@angular/material/icon';

interface Assignment {
  id: number;
  title: string;
  course: string;
  dueDate: Date;
  points: number;
  createdDate: Date;
}

@Component({
  selector: 'app-professor-assignment',
  standalone: true,
  imports: [CommonModule, FormsModule, MatIconModule],
  templateUrl: './professor-assignment.html',
  styleUrls: ['./professor-assignment.css']
})
export class ProfessorAssignmentComponent implements OnInit {
  // Form fields
  selectedCourse: string = 'CS101';
  assignmentTitle: string = '';
  assignmentDescription: string = '';
  dueDate: string = '';
  dueTime: string = '23:59';
  totalPoints: number = 100;
  selectedFile: File | null = null;

  courses = ['CS101', 'CS201', 'CS301', 'CS401'];

  recentAssignments: Assignment[] = [
    { id: 1, title: 'Problem Set 5', course: 'CS101', dueDate: new Date(2024, 10, 5), points: 100, createdDate: new Date(2024, 9, 20) },
    { id: 2, title: 'Lab Assignment 3', course: 'CS201', dueDate: new Date(2024, 10, 8), points: 50, createdDate: new Date(2024, 9, 18) },
    { id: 3, title: 'Midterm Exam', course: 'CS101', dueDate: new Date(2024, 10, 15), points: 200, createdDate: new Date(2024, 9, 10) }
  ];

  ngOnInit() {}

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  createAssignment() {
    if (!this.assignmentTitle || !this.dueDate || !this.selectedCourse) {
      alert('Please fill all required fields');
      return;
    }

    const dueDateTime = new Date(`${this.dueDate}T${this.dueTime}`);

    const newAssignment: Assignment = {
      id: this.recentAssignments.length + 1,
      title: this.assignmentTitle,
      course: this.selectedCourse,
      dueDate: dueDateTime,
      points: this.totalPoints,
      createdDate: new Date()
    };

    this.recentAssignments.unshift(newAssignment);

    // Reset form
    this.assignmentTitle = '';
    this.assignmentDescription = '';
    this.dueDate = '';
    this.dueTime = '23:59';
    this.totalPoints = 100;
    this.selectedFile = null;

    alert('Assignment created successfully!');
  }

  saveDraft() {
    alert('Assignment saved as draft!');
  }

  deleteAssignment(assignment: Assignment) {
    if (confirm('Are you sure you want to delete this assignment?')) {
      this.recentAssignments = this.recentAssignments.filter(a => a.id !== assignment.id);
    }
  }
}
