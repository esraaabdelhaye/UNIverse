import { Component, OnInit } from '@angular/core';

interface Course {
  id: number;
  code: string;
  title: string;
  professor: string;
  enrolled: number;
  progress: number;
}

@Component({
  selector: 'app-student-courses',
  templateUrl: './student-courses.component.html',
  styleUrls: ['./student-courses.component.css']
})
export class StudentCoursesComponent implements OnInit {
  searchTerm: string = '';
  courses: Course[] = [
    { id: 1, code: 'CS101', title: 'Introduction to Programming', professor: 'Prof. Alan Turing', enrolled: 118, progress: 75 },
    { id: 2, code: 'MATH201', title: 'Calculus II', professor: 'Prof. Isaac Newton', enrolled: 95, progress: 60 },
    { id: 3, code: 'PHIL301', title: 'Existentialism in Film', professor: 'Prof. Jean Sartre', enrolled: 42, progress: 85 },
    { id: 4, code: 'CHEM101', title: 'Chemistry Fundamentals', professor: 'Prof. Marie Curie', enrolled: 87, progress: 70 },
    { id: 5, code: 'ENG201', title: 'Literature & Society', professor: 'Prof. Virginia Woolf', enrolled: 64, progress: 55 },
    { id: 6, code: 'HIST212', title: 'Modern World History', professor: 'Prof. Jane Smith', enrolled: 103, progress: 80 }
  ];

  filteredCourses: Course[] = [];

  ngOnInit() {
    this.filteredCourses = [...this.courses];
  }

  onSearch() {
    this.filteredCourses = this.courses.filter(course =>
      course.code.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
      course.title.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  viewDetails(course: Course) {
    console.log('Viewing course:', course);
  }
}
