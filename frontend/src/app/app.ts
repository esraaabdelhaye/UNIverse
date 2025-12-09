import { Component, OnInit } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Navbar } from './shared/components/navbar/navbar';
import { Sidebar } from './shared/components/sidebar/sidebar';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CommonModule, FormsModule, Navbar, Sidebar],
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
  }

  logout() {
    localStorage.clear();
    this.router.navigate(['/login']);
  }
}
