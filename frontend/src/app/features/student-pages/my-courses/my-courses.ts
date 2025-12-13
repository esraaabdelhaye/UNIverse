import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, Router, ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';
import { StudentService } from '../../../core/services/student.service';

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
  private studentService = inject(StudentService);
  private route = inject(ActivatedRoute);

  // User data
  currentUser: any;

  // Course data
  courses: Course[] = [];
  filteredCourses: Course[] = [];
  searchQuery = '';

  // Loading state
  isLoading = true;
  errorMessage = '';

  ngOnInit() {
    this.currentUser = this.authService.getCurrentUser();

    if (!this.currentUser) {
      this.router.navigate(['/login']);
      return;
    }

    this.loadCourses();
  }

  loadCourses() {
    const studentId = parseInt(this.currentUser.studentId || this.currentUser.id);

    this.studentService.getStudentCourses(studentId).subscribe({
      next: (response) => {
        if (response.success && response.data) {
          const colorMap = ['blue', 'green', 'pink', 'purple', 'amber', 'red'];

          this.courses = response.data.map((course: any, index: number) => ({
            id: course.id,
            code: course.courseCode,
            name: course.courseTitle,
            professor: course.professor || 'TBA',
            bgColor: `${colorMap[index % colorMap.length]}-bg`,
            buttonColor: `${colorMap[index % colorMap.length]}-btn`,
            textColor: colorMap[index % colorMap.length],
          }));

          this.filteredCourses = [...this.courses];
        } else {
          this.errorMessage = 'No courses found';
        }
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Error loading courses:', err);
        this.errorMessage = 'Failed to load courses';
        this.isLoading = false;
      }
    });
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
      queryParams: { course: course.code, courseId: course.id }
    });
  }

  getCourseImage(courseCode: string): string {
    // Return a generic course image - in production, this could be stored per course
    return 'https://lh3.googleusercontent.com/aida-public/AB6AXuDHXeJuJN7E_Oc5z3rb7mBYsiWRhIS0crmW3GUNAyFyRQSioHpPWrQqzv0sFKqOMFUnsr2xSaCWguF2OCWQ23rxQA7iU0OVGI-MaizPOn4TAi_iJGLR-fRgbOwup5brMTH6qgh93aRo7DqlOHyw1JkuZ5JQWyO5_eA8pG0Ttyw8kc1Xl1Nvn8EyAl0lQnYq5VgcUQEC2rp_Kgjpu_WXAT9nvkAHo7Bk2wTl9U6EkkZffHUMBXx0CqwLFvtX798tZDu8rpbImtfW6eo';
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
