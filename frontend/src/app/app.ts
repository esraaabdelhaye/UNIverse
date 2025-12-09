import { Component, OnInit } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Navbar } from './shared/components/navbar/navbar';
import { Sidebar } from './shared/components/sidebar/sidebar';

// Student Components
import { StudentDashboardComponent } from './features/student-pages/student-dashboard/student-dashboard';
import { StudentCoursesComponent } from './features/student-pages/student-courses/student-courses';
import { StudentAssignmentsComponent } from './features/student-pages/student-assignments/student-assignments';
import { StudentSubmitComponent } from './features/student-pages/student-submit/student-submit';
import { StudentMaterialsComponent } from './features/student-pages/student-materials/student-materials';

// Professor Components
import { ProfessorDashboardComponent } from './features/doctor-pages/professor-dashboard/professor-dashboard';
import { ProfessorCoursesComponent } from './features/doctor-pages/professor-courses/professor-courses';
import { ProfessorGradeComponent } from './features/doctor-pages/professor-grade/professor-grade';
import { ProfessorMaterialsComponent } from './features/doctor-pages/professor-materials/professor-materials';
import { ProfessorAssignmentComponent } from './features/doctor-pages/professor-assignment/professor-assignment';

// Admin Components
import { AdminDashboardComponent } from './features/supervisor-pages/admin-dashboard/admin-dashboard';
import { AdminCoursesComponent } from './features/supervisor-pages/admin-courses/admin-courses';
import { AdminFacultyComponent } from './features/supervisor-pages/admin-faculty/admin-faculty';
import { AdminTimetableComponent } from './features/supervisor-pages/admin-timetable/admin-timetable';

// TA Components
import { TaDashboardComponent } from './features/ta-pages/ta-dashboard/ta-dashboard';
import { TaQueriesComponent } from './features/ta-pages/ta-queries/ta-queries';
import { TaAddGradesComponent } from './features/ta-pages/ta-add-grades/ta-add-grades';
import { TaSubmissionsComponent } from './features/ta-pages/ta-submissions/ta-submissions';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    CommonModule,
    FormsModule,
    Navbar,
    Sidebar,
    // Student
    StudentDashboardComponent,
    StudentCoursesComponent,
    StudentAssignmentsComponent,
    StudentSubmitComponent,
    StudentMaterialsComponent,
    // Professor
    ProfessorDashboardComponent,
    ProfessorCoursesComponent,
    ProfessorGradeComponent,
    ProfessorMaterialsComponent,
    ProfessorAssignmentComponent,
    // Admin
    AdminDashboardComponent,
    AdminCoursesComponent,
    AdminFacultyComponent,
    AdminTimetableComponent,
    // TA
    TaDashboardComponent,
    TaQueriesComponent,
    TaAddGradesComponent,
    TaSubmissionsComponent,
  ],
  templateUrl: './app.html',
  styleUrls: ['./app.css']
})
export class App implements OnInit {
  sidebarOpen: boolean = true;
  currentPage: string = 'dashboard';
  userRole: string = 'student';
  userName: string = '';
  isLoading: boolean = false;

  constructor(private router: Router) {}

  ngOnInit() {
    this.loadUserData();
  }

  loadUserData() {
    this.userRole = localStorage.getItem('userRole') || 'student';
    this.userName = localStorage.getItem('userName') || 'User';
    this.currentPage = 'dashboard';
  }

  toggleSidebar() {
    this.sidebarOpen = !this.sidebarOpen;
  }

  navigateTo(page: string) {
    this.isLoading = true;
    this.currentPage = page;
    setTimeout(() => {
      this.isLoading = false;
    }, 300);
  }

  switchRole(role: string) {
    this.userRole = role;
    this.currentPage = 'dashboard';
    localStorage.setItem('userRole', role);
    localStorage.setItem('userName', `${role.charAt(0).toUpperCase() + role.slice(1)} User`);
  }

  logout() {
    localStorage.clear();
    this.router.navigate(['/login']);
  }
}
