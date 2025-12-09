import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';

interface AssignmentItem {
  id: number;
  name: string;
  course: string;
  dueDate: Date;
  status: 'pending' | 'submitted' | 'graded' | 'late';
  score?: number;
  total?: number;
}

@Component({
  selector: 'app-student-assignments',
  standalone: true,
  imports: [CommonModule, FormsModule, MatIconModule],
  templateUrl: './student-assignments.html',
  styleUrls: ['./student-assignments.css']
})
export class StudentAssignmentsComponent implements OnInit {
  selectedTab: string = 'all';
  selectedCourse: string = 'all';

  assignments: AssignmentItem[] = [
    { id: 1, name: 'Essay 2: The Absurd Hero', course: 'PHIL-301', dueDate: new Date(2024, 9, 28), status: 'pending' },
    { id: 2, name: 'Problem Set 4', course: 'MATH-201', dueDate: new Date(2024, 10, 2), status: 'pending' },
    { id: 3, name: 'Lab Report', course: 'CHEM-101', dueDate: new Date(2024, 10, 5), status: 'late' },
    { id: 4, name: 'Essay 1', course: 'PHIL-301', dueDate: new Date(2024, 9, 15), status: 'graded', score: 92, total: 100 },
    { id: 5, name: 'Problem Set 3', course: 'MATH-201', dueDate: new Date(2024, 9, 12), status: 'graded', score: 85, total: 100 },
  ];

  filteredAssignments: AssignmentItem[] = [];

  ngOnInit() {
    this.filterAssignments();
  }

  filterAssignments() {
    let filtered = this.assignments;

    if (this.selectedTab !== 'all') {
      filtered = filtered.filter(assignment => assignment.status === this.selectedTab);
    }

    if (this.selectedCourse !== 'all') {
      filtered = filtered.filter(assignment => assignment.course === this.selectedCourse);
    }

    this.filteredAssignments = filtered;
  }

  selectTab(tab: string) {
    this.selectedTab = tab;
    this.filterAssignments();
  }

  getStatusClass(status: string): string {
    const classes: { [key: string]: string } = {
      'pending': 'status-warning',
      'submitted': 'status-info',
      'graded': 'status-success',
      'late': 'status-error'
    };
    return classes[status] || '';
  }

  getUniqueCourses(): string[] {
    return ['all', ...new Set(this.assignments.map(a => a.course))];
  }

  getPendingCount(): number {
    return this.assignments.filter(a => a.status === 'pending').length;
  }

  getSubmittedCount(): number {
    return this.assignments.filter(a => a.status === 'submitted').length;
  }

  getGradedCount(): number {
    return this.assignments.filter(a => a.status === 'graded').length;
  }
}
