import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './navbar.html',
  styleUrls: ['./navbar.css']
})
export class Navbar implements OnInit {
  @Output() toggleSidebar = new EventEmitter<void>();

  userName: string = '';
  userRole: string = '';
  notificationCount: number = 3;

  constructor(private router: Router) {}

  ngOnInit() {
    this.userName = localStorage.getItem('userName') || 'User';
    this.userRole = localStorage.getItem('userRole') || 'student';
  }

  onToggleSidebar() {
    this.toggleSidebar.emit();
  }

  onLogout() {
    localStorage.clear();
    this.router.navigate(['/login']);
  }
}
