import { Component, OnInit } from '@angular/core';

interface StatCard {
  title: string;
  count: number;
  icon: string;
  color: string;
}

interface Assignment {
  id: number;
  name: string;
  course: string;
  dueDate: Date;
  status: 'pending' | 'submitted' | 'graded';
  daysLeft: number;
}

interface Grade {
  assignment: string;
  course: string;
  score: number;
  total: number;
  date: Date;
}

@Component({
  selector: 'app-student-dashboard',
  templateUrl: './student-dashboard.component.html',
  styleUrls: ['./student-dashboard.component.css']
})
export class StudentDashboardComponent implements OnInit {
  userName: string = '';

  stats: StatCard[] = [
    { title: 'Enrolled Courses', count: 6, icon: 'menu_book', color: '#42A5F5' },
    { title: 'Upcoming Assignments', count: 12, icon: 'assignment', color: '#FF9800' },
    { title: 'Overall GPA', count: 3.8, icon: 'grade', color: '#4CAF50' },
    { title: 'Completed', count: 45, icon: 'check_circle', color: '#4CAF50' }
  ];

  assignments: Assignment[] = [
    {
      id: 1,
      name: 'Essay 2: The Absurd Hero',
      course: 'PHIL-301',
      dueDate: new Date(2024, 9, 28, 23, 59),
      status: 'pending',
      daysLeft: 5
    },
    {
      id: 2,
      name: 'Problem Set 4',
      course: 'MATH-201',
      dueDate: new Date(2024, 10, 2, 23, 59),
      status: 'pending',
      daysLeft: 9
    },
    {
      id: 3,
      name: 'Lab Report: DNA Extraction',
      course: 'CHEM-101',
      dueDate: new Date(2024, 10, 5, 23, 59),
      status: 'submitted',
      daysLeft: 12
    }
  ];

  grades: Grade[] = [
    { assignment: 'Essay 1: Existentialism', course: 'PHIL-301', score: 92, total: 100, date: new Date(2024, 9, 15) },
    { assignment: 'Problem Set 3', course: 'MATH-201', score: 85, total: 100, date: new Date(2024, 9, 12) },
    { assignment: 'Lab Report', course: 'CHEM-101', score: 88, total: 100, date: new Date(2024, 9, 10) }
  ];

  ngOnInit() {
    this.userName = localStorage.getItem('userName') || 'Student';
  }

  getUrgencyColor(daysLeft: number): string {
    if (daysLeft < 3) return '#F44336';
    if (daysLeft < 7) return '#FF9800';
    return '#BDBDBD';
  }

  getStatusBadgeClass(status: string): string {
    const classes: { [key: string]: string } = {
      'pending': 'badge-warning',
      'submitted': 'badge-info',
      'graded': 'badge-success'
    };
    return classes[status] || '';
  }
}
