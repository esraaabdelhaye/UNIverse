import { Component, OnInit } from '@angular/core';

interface Course {
  id: number;
  code: string;
  title: string;
  department: string;
  instructor: string;
  capacity: number;
  enrolled: number;
  status: 'open' | 'full' | 'closed';
}

@Component({
  selector: 'app-admin-courses',
  templateUrl: './admin-courses.component.html',
  styleUrls: ['./admin-courses.component.css']
})
export class AdminCoursesComponent implements OnInit {
  searchTerm: string = '';
  selectedDept: string = 'all';
  selectedStatus: string = 'all';

  courses: Course[] = [
    { id: 1, code: 'CS101', title: 'Introduction to Programming', department: 'Computer Science', instructor: 'Dr. Alice Johnson', capacity: 120, enrolled: 118, status: 'open' },
    { id: 2, code: 'MATH201', title: 'Calculus II', department: 'Mathematics', instructor: 'Prof. Isaac Newton', capacity: 80, enrolled: 80, status: 'full' },
    { id: 3, code: 'PHYS150', title: 'Physics I', department: 'Physics', instructor: 'Dr. Albert Einstein', capacity: 100, enrolled: 0, status: 'closed' },
    { id: 4, code: 'CHEM101', title: 'Chemistry Fundamentals', department: 'Chemistry', instructor: 'Prof. Marie Curie', capacity: 90, enrolled: 87, status: 'open' },
    { id: 5, code: 'CS201', title: 'Data Structures', department: 'Computer Science', instructor: 'Dr. Donald Knuth', capacity: 50, enrolled: 48, status: 'open' },
  ];

  filteredCourses: Course[] = [];
  departments = ['all', 'Computer Science', 'Mathematics', 'Physics', 'Chemistry'];

  ngOnInit() {
    this.filterCourses();
  }

  filterCourses() {
    this.filteredCourses = this.courses.filter(c => {
      const searchMatch = c.code.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        c.title.toLowerCase().includes(this.searchTerm.toLowerCase());
      const deptMatch = this.selectedDept === 'all' || c.department === this.selectedDept;
      const statusMatch = this.selectedStatus === 'all' || c.status === this.selectedStatus;
      return searchMatch && deptMatch && statusMatch;
    });
  }

  getCapacityColor(enrolled: number, capacity: number): string {
    const percentage = (enrolled / capacity) * 100;
    if (percentage >= 100) return '#F44336';
    if (percentage >= 90) return '#FF9800';
    return '#4CAF50';
  }

  editCourse(course: Course) {
    console.log('Edit:', course);
  }

  deleteCourse(course: Course) {
    if (confirm('Are you sure you want to delete this course?')) {
      this.courses = this.courses.filter(c => c.id !== course.id);
      this.filterCourses();
    }
  }

  viewStudents(course: Course) {
    console.log('View students for:', course);
  }
}
