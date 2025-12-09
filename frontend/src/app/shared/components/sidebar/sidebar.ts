import { Component, OnInit, Input, Output, EventEmitter, OnChanges, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';

interface NavItem {
  id: string;
  label: string;
  icon: string;
  badge?: number;
}

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, MatIconModule],
  templateUrl: './sidebar.html',
  styleUrls: ['./sidebar.css']
})
export class Sidebar implements OnInit, OnChanges {
  @Input() isOpen: boolean = true;
  @Input() currentPage: string = 'dashboard';
  @Input() userRole: string = 'student';
  @Output() navigate = new EventEmitter<string>();

  navItems: NavItem[] = [];

  ngOnInit() {
    this.setNavItems();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['userRole']) {
      this.setNavItems();
    }
  }

  setNavItems() {
    const configs: { [key: string]: NavItem[] } = {
      student: [
        { id: 'dashboard', label: 'Dashboard', icon: 'home' },
        { id: 'courses', label: 'My Courses', icon: 'menu_book' },
        { id: 'assignments', label: 'Assignments & Grades', icon: 'assignment' },
        { id: 'submit', label: 'Submit Assignment', icon: 'cloud_upload', badge: 2 },
        { id: 'materials', label: 'Course Materials', icon: 'folder' },
      ],
      professor: [
        { id: 'dashboard', label: 'Dashboard', icon: 'home' },
        { id: 'courses', label: 'My Courses', icon: 'menu_book' },
        { id: 'grade', label: 'Grade Submissions', icon: 'grading', badge: 5 },
        { id: 'materials', label: 'Upload Materials', icon: 'cloud_upload' },
        { id: 'assignment', label: 'Create Assignment', icon: 'add_circle' },
      ],
      admin: [
        { id: 'dashboard', label: 'Dashboard', icon: 'home' },
        { id: 'courses', label: 'Manage Courses', icon: 'menu_book' },
        { id: 'faculty', label: 'Manage Faculty', icon: 'people' },
        { id: 'timetable', label: 'View Timetable', icon: 'calendar_today' },
      ],
      ta: [
        { id: 'dashboard', label: 'Dashboard', icon: 'home' },
        { id: 'queries', label: 'Student Queries', icon: 'help', badge: 3 },
        { id: 'addgrades', label: 'Add Grades', icon: 'grading' },
        { id: 'submissions', label: 'View Submissions', icon: 'visibility' },
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

  onNavigate(page: string) {
    this.navigate.emit(page);
  }
}
