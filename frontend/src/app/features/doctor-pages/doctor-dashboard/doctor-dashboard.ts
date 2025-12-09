import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

interface Course {
  code: string;
  name: string;
  students: number;
}

interface Stat {
  label: string;
  value: number;
  icon: string;
}

@Component({
  selector: 'app-doctor-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './doctor-dashboard.html',
  styleUrl: './doctor-dashboard.css',
})
export class DoctorDashboard implements OnInit {
  private router = inject(Router);
  private authService = inject(AuthService);

  courses: Course[] = [];
  stats: Stat[] = [];

  ngOnInit() {
    this.loadCourses();
    this.loadStats();
  }

  loadCourses() {
    this.courses = [
      { code: 'PHIL-301', name: 'Existentialism in Film', students: 42 },
      { code: 'HIST-212', name: 'Renaissance Art History', students: 35 },
      { code: 'LIT-405', name: 'Modernist Poetry', students: 28 },
      { code: 'PHIL-101', name: 'Introduction to Logic', students: 112 },
    ];
  }

  loadStats() {
    this.stats = [
      { label: 'Pending Submissions', value: 12, icon: 'assignment' },
      { label: 'To Grade', value: 8, icon: 'pending_actions' },
      { label: 'Total Students', value: 217, icon: 'people' },
    ];
  }

  navigateToCourse(courseCode: string) {
    this.router.navigate(['/grade-students'], { queryParams: { course: courseCode } });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
