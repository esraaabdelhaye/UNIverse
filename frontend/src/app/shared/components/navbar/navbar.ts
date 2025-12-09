import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatMenuModule } from '@angular/material/menu';
import { MatDividerModule } from '@angular/material/divider';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    CommonModule,
    MatMenuModule,
    MatDividerModule,
    MatButtonModule,
    MatIconModule
  ],
  templateUrl: './navbar.html',
  styleUrls: ['./navbar.css']
})
export class Navbar implements OnInit {
  @Output() toggleSidebar = new EventEmitter<void>();
  @Output() logout = new EventEmitter<void>();
  @Input() userName: string = '';
  @Input() userRole: string = '';

  notificationCount: number = 3;

  constructor(private router: Router) {}

  ngOnInit() {
    if (!this.userName) {
      this.userName = localStorage.getItem('userName') || 'User';
    }
    if (!this.userRole) {
      this.userRole = localStorage.getItem('userRole') || 'student';
    }
  }

  onToggleSidebar() {
    this.toggleSidebar.emit();
  }

  onLogout() {
    this.logout.emit();
  }
}
