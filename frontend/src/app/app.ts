import { Component, OnInit } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Navbar } from './shared/components/navbar/navbar';
import { Sidebar } from './shared/components/sidebar/sidebar';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    CommonModule,
    FormsModule,
    Navbar,
    Sidebar,
  ],
  templateUrl: './app.html',
  styleUrls: ['./app.css']
})
export class App implements OnInit {
  sidebarOpen: boolean = true;
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
  }

  toggleSidebar() {
    this.sidebarOpen = !this.sidebarOpen;
  }

  navigateTo(page: string) {
    this.isLoading = true;

    // Role-based route mapping
    const routeMap: { [key: string]: { [key: string]: string } } = {
      student: {
        dashboard: '/student-dashboard',
        courses: '/student-courses',
        assignments: '/student-assignments',
        submit: '/student-submit',
        materials: '/student-materials',
      },
      professor: {
        dashboard: '/professor-dashboard',
        courses: '/professor-courses',
        grade: '/professor-grade',
        materials: '/professor-materials',
        assignment: '/professor-assignment',
      },
      admin: {
        dashboard: '/admin-dashboard',
        courses: '/admin-courses',
        faculty: '/admin-faculty',
        timetable: '/admin-timetable',
      },
      ta: {
        dashboard: '/ta-dashboard',
        queries: '/ta-queries',
        addgrades: '/ta-addgrades',
        submissions: '/ta-submissions',
      },
    };

    const routes = routeMap[this.userRole] || {};
    const route = routes[page] || `/${this.userRole}-dashboard`;

    this.router.navigate([route]).finally(() => {
      this.isLoading = false;
    });
  }

  switchRole(role: string) {
    this.userRole = role;
    localStorage.setItem('userRole', role);
    localStorage.setItem('userName', `${role.charAt(0).toUpperCase() + role.slice(1)} User`);
    this.userName = localStorage.getItem('userName') || 'User';

    // Navigate to the role's dashboard
    const dashboardRoute = `/${role}-dashboard`;
    this.router.navigate([dashboardRoute]);
  }

  logout() {
    localStorage.clear();
    this.router.navigate(['/login']);
  }
}
