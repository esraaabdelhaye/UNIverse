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

  loadAssignments(): void {
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

  filterAssignments(): void {
    let filtered = [...this.assignments];

    // Filter by course if selected
    if (this.selectedCourse) {
      filtered = filtered.filter(a => a.course === this.selectedCourse);
    }

    // Sort by due date or course
    if (this.selectedSort === 'course') {
      filtered.sort((a, b) => a.course.localeCompare(b.course));
    } else {
      // Default: sort by due date (pending first)
      filtered.sort((a, b) => {
        if (a.status === 'pending' && b.status !== 'pending') return -1;
        if (a.status !== 'pending' && b.status === 'pending') return 1;
        return 0;
      });
    }

    this.filteredAssignments = filtered;
  }

  onCourseChange(): void {
    this.filterAssignments();
  }

  onSortChange(): void {
    this.filterAssignments();
  }

  submitAssignment(assignment: Assignment): void {
    if (assignment.status !== 'pending') {
      alert('This assignment cannot be submitted in its current status');
      return;
    }

    assignment.status = 'submitted';
    console.log('Submitted:', assignment.title);
    alert(`Assignment "${assignment.title}" submitted successfully!`);
  }

  viewSubmission(assignment: Assignment): void {
    if (assignment.status === 'pending') {
      alert('No submission yet');
      return;
    }

    console.log('Viewing submission:', assignment.id);
    alert(`Viewing submission for: ${assignment.title}\n\nSubmission details would load here.`);
  }

  resubmit(assignment: Assignment): void {
    if (assignment.status !== 'submitted' && assignment.status !== 'graded') {
      alert('Cannot resubmit this assignment');
      return;
    }

    assignment.status = 'submitted';
    console.log('Resubmitting:', assignment.title);
    alert(`Resubmitted "${assignment.title}". Please upload your file.`);
  }

  viewGrade(assignment: Assignment): void {
    if (!assignment.grade) {
      alert('Grade not available yet');
      return;
    }

    const letterGrade = assignment.grade >= 90 ? 'A' :
      assignment.grade >= 80 ? 'B' :
        assignment.grade >= 70 ? 'C' :
          assignment.grade >= 60 ? 'D' : 'F';

    alert(`Grade for ${assignment.title}:\n\n${assignment.grade}% (${letterGrade})`);
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
