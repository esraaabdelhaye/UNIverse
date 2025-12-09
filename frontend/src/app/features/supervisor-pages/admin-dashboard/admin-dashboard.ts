import { Component, OnInit } from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-dashboard.html',
  styleUrls: ['./admin-dashboard.css']
})
export class AdminDashboardComponent implements OnInit {
  stats = [
    { title: 'Total Users', count: 1254, icon: 'people', color: '#26A69A' },
    { title: 'Active Courses', count: 86, icon: 'book', color: '#26A69A' },
    { title: 'Faculty Members', count: 125, icon: 'school', color: '#26A69A' },
    { title: 'System Status', count: 'Operational', icon: 'check_circle', color: '#4CAF50' }
  ];

  quickActions = [
    { title: 'Manage Courses', icon: 'book', action: 'courses' },
    { title: 'Manage Faculty', icon: 'people', action: 'faculty' },
    { title: 'View Timetable', icon: 'calendar', action: 'timetable' },
    { title: 'System Settings', icon: 'settings', action: 'settings' }
  ];

  recentActivities = [
    { type: 'course', message: 'New course created: CS501 - Advanced AI', timestamp: new Date(Date.now() - 2 * 60 * 60 * 1000) },
    { type: 'faculty', message: 'Prof. Sarah Johnson added to Mathematics department', timestamp: new Date(Date.now() - 5 * 60 * 60 * 1000) },
    { type: 'alert', message: 'MATH201 at 90% capacity', timestamp: new Date(Date.now() - 8 * 60 * 60 * 1000) },
    { type: 'course', message: 'PHYS150 enrollment closed', timestamp: new Date(Date.now() - 24 * 60 * 60 * 1000) }
  ];

  departments = [
    { name: 'Computer Science', courses: 24, faculty: 18, students: 450 },
    { name: 'Mathematics', courses: 16, faculty: 12, students: 320 },
    { name: 'Physics', courses: 14, faculty: 10, students: 280 },
    { name: 'Chemistry', courses: 12, faculty: 8, students: 240 }
  ];

  ngOnInit() {}

  handleAction(action: string) {
    console.log('Action:', action);
  }
}
