import { Component, OnInit } from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';

interface CourseItem {
  id: number;
  code: string;
  title: string;
  department: string;
  students: number;
  capacity: number;
}

@Component({
  selector: 'app-professor-courses',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './professor-courses.html',
  styleUrls: ['./professor-courses.css']
})
export class ProfessorCoursesComponent implements OnInit {
  searchTerm: string = '';

  courses: CourseItem[] = [
    { id: 1, code: 'CS101', title: 'Introduction to Programming', department: 'Computer Science', students: 45, capacity: 50 },
    { id: 2, code: 'CS201', title: 'Data Structures', department: 'Computer Science', students: 38, capacity: 40 },
    { id: 3, code: 'CS301', title: 'Algorithms', department: 'Computer Science', students: 32, capacity: 35 },
    { id: 4, code: 'CS401', title: 'Advanced Topics', department: 'Computer Science', students: 28, capacity: 30 }
  ];

  filteredCourses: CourseItem[] = [];

  ngOnInit() {
    this.filteredCourses = [...this.courses];
  }

  onSearch() {
    this.filteredCourses = this.courses.filter(c =>
      c.code.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
      c.title.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  getCapacityColor(students: number, capacity: number): string {
    const percentage = (students / capacity) * 100;
    if (percentage >= 100) return '#F44336';
    if (percentage >= 90) return '#FF9800';
    return '#4CAF50';
  }

  viewRoster(course: CourseItem) {
    console.log('View roster for:', course);
  }

  createAssignment(course: CourseItem) {
    console.log('Create assignment for:', course);
  }

  uploadMaterial(course: CourseItem) {
    console.log('Upload material for:', course);
  }

  viewGrades(course: CourseItem) {
    console.log('View grades for:', course);
  }
}
