import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, Router, ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';
import { DoctorService } from '../../../core/services/doctor.service';
import { Course } from '../../../core/models/course.model';
import { Student } from '../../../core/models/student.model';
import { CourseEnrollment } from '../../../core/models/enrollment.model';
import { CourseService } from '../../../core/services/course.service';
import { StudentService } from '../../../core/services/student.service';

@Component({
  selector: 'app-course-students',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive, FormsModule],
  templateUrl: './course-students.html',
  styleUrl: './course-students.css',
})
export class CourseStudents implements OnInit {
  private router = inject(Router);
  private authService = inject(AuthService);
  private route = inject(ActivatedRoute);
  private doctorService = inject(DoctorService);
  private courseService = inject(CourseService);

  // Data
  currentDoctor: any;
  courses: Course[] = [];
  enrollments: CourseEnrollment[] = [];
  students: Student[] = [];

  // If a `course` query param arrives before courses finish loading we'll store it here
  pendingCourseCode: string | null = null;

  selectedCourse: number | '' = '';
  isLoading = false;

  ngOnInit() {
    this.currentDoctor = this.authService.getCurrentUser();
    this.loadCourses();

    // Check for course parameter from URL
    this.route.queryParams.subscribe((params) => {
      if (params['course']) {
        const code = params['course'];

        // If courses not loaded yet, save the code and apply it after load
        if (!this.courses || this.courses.length === 0) {
          this.pendingCourseCode = code;
          return;
        }

        const course = this.courses.find((c) => c.courseCode === code);
        if (course) {
          this.selectedCourse = course.id;
          this.onCourseChange();
        }
      }
    });
  }

  loadCourses() {
    this.isLoading = true;
    this.doctorService.getDoctorCourses(this.currentDoctor.doctorId).subscribe({
      next: (response) => {
        if (response.success && response.data) {
          const courses = response.data;
          this.courses = Array.isArray(courses) ? courses : courses ? [courses] : [];
          console.log('Loaded courses:', this.courses);

          // If there was a pending course query param (arrived earlier), apply it now
          if (this.pendingCourseCode) {
            const course = this.courses.find((c) => c.courseCode === this.pendingCourseCode);
            if (course) {
              this.selectedCourse = course.id;
              this.onCourseChange();
            }
            this.pendingCourseCode = null;
          }

          this.isLoading = false;
        }
      },
      error: (err) => {
        console.error('Could not load courses:', err);
        this.isLoading = false;
      },
    });
  }

  onCourseChange() {
    if (this.selectedCourse === '' || this.selectedCourse == null) {
      this.students = [];
      return;
    }

    this.isLoading = true;
    this.students = [];

    // Load enrollments for the selected course
    this.courseService.getCourseEnrollments(this.selectedCourse as number).subscribe({
      next: (enrollResponse) => {
        if (enrollResponse.success && enrollResponse.data) {
          const enrollments = Array.isArray(enrollResponse.data)
            ? enrollResponse.data
            : [enrollResponse.data];

          console.log('Enrollments:', enrollments);

          // Load student details for each enrollment
          let loadedCount = 0;
          const totalEnrollments = enrollments.length;

          if (totalEnrollments === 0) {
            this.isLoading = false;
            return;
          }

          enrollments.forEach((enrollment: CourseEnrollment) => {
            this.students = this.students.concat(enrollment.student ? [enrollment.student] : []);
            loadedCount++;
          });
          this.isLoading = false;
          console.log('Loaded students:', this.students);
        } else {
          this.isLoading = false;
        }
      },
      error: (err) => {
        console.error('Error loading enrollments:', err);
        this.isLoading = false;
      },
    });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
