import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';

interface Assignment {
  id: number;
  title: string;
  course: string;
  dueDate: string;
  status: 'pending' | 'submitted' | 'graded' | 'pastdue';
  grade?: number;
}

@Component({
  selector: 'app-submit-assignments',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive, FormsModule],
  templateUrl: './submit-assignments.html',
  styleUrl: './submit-assignments.css',
})
export class SubmitAssignments implements OnInit {
  private router = inject(Router);
  private authService = inject(AuthService);

  assignments: Assignment[] = [];
  filteredAssignments: Assignment[] = [];
  selectedCourse = '';
  selectedSort = 'duedate';

  ngOnInit() {
    this.loadAssignments();
  }

  loadAssignments() {
    this.assignments = [
      {
        id: 1,
        title: 'Assignment 1: Basic Algorithms',
        course: 'CS101',
        dueDate: 'Today, 11:59 PM',
        status: 'pending',
      },
      {
        id: 2,
        title: 'Lab Report: Linked Lists',
        course: 'CS202',
        dueDate: 'In 3 days',
        status: 'pending',
      },
      {
        id: 3,
        title: 'Project Proposal',
        course: 'DS310',
        dueDate: 'Oct 28, 2024',
        status: 'submitted',
      },
      {
        id: 4,
        title: 'Quiz 1: Programming Fundamentals',
        course: 'CS101',
        dueDate: 'Oct 15, 2024',
        status: 'graded',
        grade: 95,
      },
      {
        id: 5,
        title: 'Homework 2: API Integration',
        course: 'DS310',
        dueDate: 'Oct 10, 2024',
        status: 'pastdue',
      },
    ];
    this.filterAssignments();
  }

  filterAssignments() {
    let filtered = [...this.assignments];

    if (this.selectedCourse) {
      filtered = filtered.filter(a => a.course === this.selectedCourse);
    }

    if (this.selectedSort === 'course') {
      filtered.sort((a, b) => a.course.localeCompare(b.course));
    }

    this.filteredAssignments = filtered;
  }

  submitAssignment(assignment: Assignment) {
    assignment.status = 'submitted';
    alert(`Assignment "${assignment.title}" submitted successfully!`);
  }

  viewSubmission(assignment: Assignment) {
    console.log('Viewing submission:', assignment);
    alert(`Viewing submission for "${assignment.title}"`);
  }

  resubmit(assignment: Assignment) {
    alert(`Resubmitting "${assignment.title}"`);
  }

  viewGrade(assignment: Assignment) {
    alert(`Grade for "${assignment.title}": ${assignment.grade}%`);
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
