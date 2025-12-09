import { Component, OnInit, Input, Output, EventEmitter, OnChanges } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

interface NavItem {
  id: string;
  label: string;
  icon: string;
  badge?: number;
}

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './sidebar.html',
  styleUrls: ['./sidebar.css']
})
export class Sidebar implements OnInit, OnChanges {
  @Input() isOpen: boolean = true;
  @Input() currentPage: string = 'dashboard';
  @Input() userRole: string = 'student';
  @Output() navigate = new EventEmitter<string>();

  navItems: NavItem[] = [];

  constructor(private router: Router) {}

  ngOnInit() {
    this.setNavItems();
  }

  ngOnChanges() {
    this.setNavItems();
  }

  setNavItems() {
    const configs: { [key: string]: NavItem[] } = {
      student: [
        { id: 'dashboard', label: 'Dashboard', icon: 'home' },
        { id: 'courses', label: 'My Courses', icon: 'book' },
        { id: 'assignments', label: 'Assignments & Grades', icon: 'file-text' },
        { id: 'submit', label: 'Submit Assignment', icon: 'upload', badge: 2 },
        { id: 'materials', label: 'Course Materials', icon: 'folder' },
      ],
      professor: [
        { id: 'dashboard', label: 'Dashboard', icon: 'home' },
        { id: 'courses', label: 'My Courses', icon: 'book' },
        { id: 'grade', label: 'Grade Submissions', icon: 'file-text', badge: 5 },
        { id: 'materials', label: 'Upload Materials', icon: 'upload' },
        { id: 'assignment', label: 'Create Assignment', icon: 'plus' },
      ],
      admin: [
        { id: 'dashboard', label: 'Dashboard', icon: 'home' },
        { id: 'courses', label: 'Manage Courses', icon: 'book' },
        { id: 'faculty', label: 'Manage Faculty', icon: 'users' },
        { id: 'timetable', label: 'View Timetable', icon: 'calendar' },
      ],
      ta: [
        { id: 'dashboard', label: 'Dashboard', icon: 'home' },
        { id: 'queries', label: 'Student Queries', icon: 'help-circle', badge: 3 },
        { id: 'addgrades', label: 'Add Grades', icon: 'file-text' },
        { id: 'submissions', label: 'View Submissions', icon: 'eye' },
      ],
    };

    this.navItems = configs[this.userRole] || [];
  }

  getRoleColor(): string {
    const colors: { [key: string]: string } = {
      student: '#42A5F5',
      professor: '#66BB6A',
      admin: '#26A69A',
      ta: '#FFB74D'
    };
    return colors[this.userRole] || '#1976D2';
  }

  getRoleName(): string {
    const names: { [key: string]: string } = {
      student: 'Student',
      professor: 'Professor',
      admin: 'Administrator',
      ta: 'Teaching Assistant'
    };
    return names[this.userRole] || 'User';
  }

  getIconSvg(icon: string): string {
    const icons: { [key: string]: string } = {
      home: '<svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path><polyline points="9 22 9 12 15 12 15 22"></polyline></svg>',
      book: '<svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M4 19.5A2.5 2.5 0 0 1 6.5 17H20"></path><path d="M6.5 2H20v20H6.5A2.5 2.5 0 0 1 4 19.5v-15A2.5 2.5 0 0 1 6.5 2z"></path></svg>',
      'file-text': '<svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path><polyline points="14 2 14 8 20 8"></polyline></svg>',
      upload: '<svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"></path><polyline points="17 8 12 3 7 8"></polyline><line x1="12" y1="3" x2="12" y2="15"></line></svg>',
      folder: '<svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M22 19a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h5l2 3h9a2 2 0 0 1 2 2z"></path></svg>',
      plus: '<svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><line x1="12" y1="5" x2="12" y2="19"></line><line x1="5" y1="12" x2="19" y2="12"></line></svg>',
      users: '<svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path><circle cx="9" cy="7" r="4"></circle><path d="M23 21v-2a4 4 0 0 0-3-3.87"></path><path d="M16 3.13a4 4 0 0 1 0 7.75"></path></svg>',
      calendar: '<svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="4" width="18" height="18" rx="2" ry="2"></rect><line x1="16" y1="2" x2="16" y2="6"></line><line x1="8" y1="2" x2="8" y2="6"></line><line x1="3" y1="10" x2="21" y2="10"></line></svg>',
      'help-circle': '<svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="10"></circle><path d="M12 16v-4"></path><path d="M12 8h.01"></path></svg>',
      eye: '<svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path><circle cx="12" cy="12" r="3"></circle></svg>',
    };
    return icons[icon] || '';
  }

  onNavigate(page: string) {
    this.navigate.emit(page);
    // Navigate to the actual page
    this.router.navigate([`/${this.userRole}-${page}`]);
  }
}
