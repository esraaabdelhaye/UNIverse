import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';

interface Course {
  id: number;
  code: string;
  name: string;
  professor: string;
  bgColor: string;
  buttonColor: string;
  textColor: string;
}

@Component({
  selector: 'app-my-courses',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive, FormsModule],
  templateUrl: './my-courses.html',
  styleUrl: './my-courses.css',
})
export class MyCourses implements OnInit {
  private router = inject(Router);
  private authService = inject(AuthService);

  courses: Course[] = [];
  filteredCourses: Course[] = [];
  searchQuery = '';

  ngOnInit() {
    this.loadCourses();
  }

  loadCourses() {
    this.courses = [
      {
        id: 1,
        code: 'CS101',
        name: 'Intro to Programming',
        professor: 'Prof. Alan Turing',
        bgColor: 'blue-bg',
        buttonColor: 'blue-btn',
        textColor: 'blue',
      },
      {
        id: 2,
        code: 'CS202',
        name: 'Data Structures',
        professor: 'Prof. Ada Lovelace',
        bgColor: 'green-bg',
        buttonColor: 'green-btn',
        textColor: 'green',
      },
      {
        id: 3,
        code: 'DS310',
        name: 'Web Development',
        professor: 'Prof. Tim Berners-Lee',
        bgColor: 'pink-bg',
        buttonColor: 'pink-btn',
        textColor: 'pink',
      },
      {
        id: 4,
        code: 'AI404',
        name: 'Artificial Intelligence',
        professor: 'Prof. Geoffrey Hinton',
        bgColor: 'purple-bg',
        buttonColor: 'purple-btn',
        textColor: 'purple',
      },
      {
        id: 5,
        code: 'DB521',
        name: 'Database Systems',
        professor: 'Prof. Edgar Codd',
        bgColor: 'amber-bg',
        buttonColor: 'amber-btn',
        textColor: 'amber',
      },
      {
        id: 6,
        code: 'MA110',
        name: 'Calculus I',
        professor: 'Prof. Isaac Newton',
        bgColor: 'red-bg',
        buttonColor: 'red-btn',
        textColor: 'red',
      },
    ];
    this.filteredCourses = [...this.courses];
  }

  searchCourses() {
    const query = this.searchQuery.toLowerCase();
    this.filteredCourses = this.courses.filter(
      course =>
        course.name.toLowerCase().includes(query) ||
        course.code.toLowerCase().includes(query) ||
        course.professor.toLowerCase().includes(query)
    );
  }

  viewCourse(course: Course) {
    this.router.navigate(['/student-dashboard/view-materials'], {
      queryParams: { course: course.code }
    });
    console.log('Viewing course:', course.code);
  }

  getCourseImage(courseCode: string): string {
    // Return appropriate image based on course code
    return 'https://lh3.googleusercontent.com/aida-public/AB6AXuDHXeJuJN7E_Oc5z3rb7mBYsiWRhIS0crmW3GUNAyFyRQSioHpPWrQqzv0sFKqOMFUnsr2xSaCWguF2OCWQ23rxQA7iU0OVGI-MaizPOn4TAi_iJGLR-fRgbOwup5brMTH6qgh93aRo7DqlOHyw1JkuZ5JQWyO5_eA8pG0Ttyw8kc1Xl1Nvn8EyAl0lQnYq5VgcUQEC2rp_Kgjpu_WXAT9nvkAHo7Bk2wTl9U6EkkZffHUMBXx0CqwLFvtX798tZDu8rpbImtfW6eo';
  }

  enrollCourse(course: Course): void {
    alert(`You are now enrolled in ${course.name}!`);
    console.log('Enrolled in course:', course.code);
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
