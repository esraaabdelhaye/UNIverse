import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  sidebarOpen: boolean = true;
  currentPage: string = 'dashboard';
  userRole: string = 'student'; // student | professor | admin | ta
  userName: string = '';
  isLoading: boolean = false;

  constructor(private router: Router) {}

  ngOnInit() {
    this.loadUserData();
  }

  loadUserData() {
    // Get user data from localStorage (set during login)
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

    // Simulate page load delay
    setTimeout(() => {
      this.isLoading = false;
    }, 300);
  }

  switchRole(role: string) {
    this.userRole = role;
    this.currentPage = 'dashboard';
    localStorage.setItem('userRole', role);
  }

  getPageComponent(): string {
    return `${this.userRole}-${this.currentPage}`;
  }

  logout() {
    localStorage.clear();
    this.router.navigate(['/login']);
  }
}
