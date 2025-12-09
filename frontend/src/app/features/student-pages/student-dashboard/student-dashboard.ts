import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

interface Deadline {
  title: string;
  course: string;
  dueDate: string;
  urgency: 'urgent' | 'warning' | 'normal';
}

interface Grade {
  course: string;
  assignment: string;
  score: number;
}

interface Course {
  code: string;
  name: string;
  professor: string;
  progress: number;
}

@Component({
  selector: 'app-student-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './student-dashboard.html',
  styleUrl: './student-dashboard.css',
})
export class StudentDashboard implements OnInit {
  private router = inject(Router);
  private authService = inject(AuthService);

  deadlines: Deadline[] = [];
  grades: Grade[] = [];
  courses: Course[] = [];

  ngOnInit() {
    this.loadDeadlines();
    this.loadGrades();
    this.loadCourses();
  }

  loadDeadlines() {
    this.deadlines = [
      {
        title: 'CS101 - Assignment 1',
        course: 'CS101',
        dueDate: 'Today',
        urgency: 'urgent',
      },
      {
        title: 'CS202 - Lab Report',
        course: 'CS202',
        dueDate: 'In 3 days',
        urgency: 'warning',
      },
      {
        title: 'DS310 - Project Proposal',
        course: 'DS310',
        dueDate: 'Oct 28',
        urgency: 'normal',
      },
    ];
  }

  loadGrades() {
    this.grades = [
      { course: 'CS101', assignment: 'Quiz 1', score: 95 },
      { course: 'DS310', assignment: 'Homework 2', score: 82 },
    ];
  }

  loadCourses() {
    this.courses = [
      {
        code: 'CS101',
        name: 'Intro to Programming',
        professor: 'Prof. Alan Turing',
        progress: 75,
      },
      {
        code: 'CS202',
        name: 'Data Structures',
        professor: 'Prof. Ada Lovelace',
        progress: 45,
      },
      {
        code: 'DS310',
        name: 'Web Development',
        professor: 'Prof. Tim Berners-Lee',
        progress: 60,
      },
    ];
  }

  postQuestion() {
    alert('Question posted successfully!');
  }

  navigateToCourse(courseCode: string): void {
    this.router.navigate(['/student-dashboard/my-courses'], {
      queryParams: { course: courseCode }
    });
    console.log('Navigating to course:', courseCode);
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
