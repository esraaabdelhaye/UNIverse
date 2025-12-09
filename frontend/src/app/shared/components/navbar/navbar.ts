import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
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
