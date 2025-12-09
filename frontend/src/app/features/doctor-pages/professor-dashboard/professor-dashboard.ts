import { Component, OnInit } from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {MatIconModule} from '@angular/material/icon';

@Component({
  selector: 'app-professor-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule, MatIconModule],
  templateUrl: './professor-dashboard.html',
  styleUrls: ['./professor-dashboard.css']
})
export class ProfessorDashboardComponent implements OnInit {
  userName: string = '';

  stats = [
    { title: 'Total Students', count: 245, icon: 'people', color: '#42A5F5' },
    { title: 'Active Courses', count: 4, icon: 'menu_book', color: '#66BB6A' },
    { title: 'Pending Grading', count: 23, icon: 'assignment', color: '#FF9800' },
    { title: 'Teaching Hours/Week', count: 12, icon: 'schedule', color: '#4CAF50' }
  ];

  courses = [
    { code: 'CS101', title: 'Introduction to Programming', students: '45/50', icon: 'code' },
    { code: 'CS201', title: 'Data Structures', students: '38/40', icon: 'storage' },
    { code: 'CS301', title: 'Algorithms', students: '32/35', icon: 'timeline' },
    { code: 'CS401', title: 'Advanced Topics', students: '130/200', icon: 'school' }
  ];

  gradingQueue = [
    { assignment: 'Problem Set 5', course: 'CS101', pending: 12, dueDate: new Date(2024, 10, 5) },
    { assignment: 'Midterm Exam', course: 'CS201', pending: 8, dueDate: new Date(2024, 10, 3) }
  ];

  ngOnInit() {
    this.userName = localStorage.getItem('userName') || 'Professor';
  }
}
