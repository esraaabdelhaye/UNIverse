import { Component, OnInit } from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-ta-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './ta-dashboard.html',
  styleUrls: ['./ta-dashboard.css']
})
export class TaDashboardComponent implements OnInit {
  userName: string = '';

  stats = [
    { title: 'Pending Queries', count: 8, icon: 'help-circle', color: '#FFB74D', badge: true },
    { title: 'Submissions to Grade', count: 23, icon: 'file-text', color: '#FFB74D', badge: true },
    { title: 'Assigned Courses', count: 3, icon: 'menu_book', color: '#FFB74D', badge: false }
  ];

  assignedCourses = [
    { code: 'CS101', title: 'Introduction to Programming', professor: 'Dr. Alice Johnson', students: 120 },
    { code: 'MATH201', title: 'Calculus II', professor: 'Prof. Isaac Newton', students: 80 },
    { code: 'PHYS150', title: 'Physics I', professor: 'Dr. Albert Einstein', students: 100 }
  ];

  recentQueries = [
    { id: 1, studentName: 'John Doe', course: 'CS101', subject: 'Question about recursion', timestamp: new Date(Date.now() - 2 * 60 * 60 * 1000), status: 'new' },
    { id: 2, studentName: 'Jane Smith', course: 'MATH201', subject: 'Integration by parts clarification', timestamp: new Date(Date.now() - 5 * 60 * 60 * 1000), status: 'new' },
    { id: 3, studentName: 'Bob Wilson', course: 'CS101', subject: 'Loop implementation help', timestamp: new Date(Date.now() - 8 * 60 * 60 * 1000), status: 'responded' }
  ];

  gradingQueue = [
    { course: 'CS101', assignment: 'Problem Set 5', pending: 12 },
    { course: 'MATH201', assignment: 'Calculus Quiz', pending: 8 },
    { course: 'PHYS150', assignment: 'Lab Report', pending: 3 }
  ];

  ngOnInit() {
    this.userName = localStorage.getItem('userName') || 'Teaching Assistant';
  }

  viewQuery(query: any) {
    console.log('View query:', query);
  }

  gradeAssignment(item: any) {
    console.log('Grade:', item);
  }
}
