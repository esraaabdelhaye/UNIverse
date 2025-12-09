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
    console.log('Viewing course:', course);
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
