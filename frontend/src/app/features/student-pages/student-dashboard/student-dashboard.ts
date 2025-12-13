import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { StudentService } from '../../../core/services/student.service';
import { CourseService } from '../../../core/services/course.service';
import { GradeService } from '../../../core/services/grade.service';

interface Deadline {
  id: number;
  title: string;
  course: string;
  dueDate: string;
  urgency: 'urgent' | 'warning' | 'normal';
}

interface Grade {
  course: string;
  assignment: string;
  score: number;
}

interface Course {
  id: number;
  code: string;
  name: string;
  professor: string;
  progress: number;
}

@Component({
  selector: 'app-student-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './student-dashboard.html',
  styleUrl: './student-dashboard.css',
})
export class StudentDashboard implements OnInit {
  private router = inject(Router);
  private authService = inject(AuthService);
  private studentService = inject(StudentService);
  private courseService = inject(CourseService);
  private gradeService = inject(GradeService);

  deadlines: Deadline[] = [];
  grades: Grade[] = [];
  courses: Course[] = [];
  isLoading = true;
  errorMessage = '';

  ngOnInit() {
    this.loadStudentData();
  }

  loadStudentData(): void {
    const currentUser = this.authService.getCurrentUser();
    if (!currentUser) {
      this.router.navigate(['/login']);
      return;
    }

    const studentId = parseInt(currentUser.studentId || currentUser.id);

    // Load courses
    this.studentService.getStudentCourses(studentId).subscribe({
      next: (response) => {
        if (response.success && response.data) {
          this.courses = response.data.map((course: any) => ({
            id: course.id,
            code: course.courseCode,
            name: course.courseTitle,
            professor: course.professor || 'TBA',
            progress: Math.floor(Math.random() * 100),
          }));
        }
      },
      error: (err) => {
        console.error('Error loading courses:', err);
        this.errorMessage = 'Failed to load courses';
      }
    });

    // Load grades
    this.gradeService.getStudentGrades(studentId).subscribe({
      next: (response) => {
        if (response.success && response.data) {
          this.grades = response.data.slice(0, 2).map((grade: any) => ({
            course: grade.courseCode,
            assignment: grade.gradingStatus,
            score: grade.score || 0,
          }));
        }
      },
      error: (err) => {
        console.error('Error loading grades:', err);
      }
    });

    // Load deadlines
    this.studentService.getStudentAssignments(studentId).subscribe({
      next: (response) => {
        if (response.success && response.data) {
          this.deadlines = response.data.slice(0, 3).map((assignment: any, index: number) => ({
            id: index,
            title: assignment.assignmentTitle || assignment.title,
            course: assignment.courseCode,
            dueDate: assignment.dueDate,
            urgency: this.getUrgency(assignment.dueDate),
          }));
        }
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Error loading assignments:', err);
        this.isLoading = false;
      }
    });
  }

  private getUrgency(dueDate: string): 'urgent' | 'warning' | 'normal' {
    const date = new Date(dueDate);
    const today = new Date();
    const daysUntil = (date.getTime() - today.getTime()) / (1000 * 60 * 60 * 24);

    if (daysUntil <= 0) return 'urgent';
    if (daysUntil <= 3) return 'warning';
    return 'normal';
  }

  navigateToCourse(courseCode: string): void {
    this.router.navigate(['/student-dashboard/my-courses'], {
      queryParams: { course: courseCode }
    });
  }

  getLetterGrade(score: number): string {
    if (score >= 90) return 'A';
    if (score >= 80) return 'B';
    if (score >= 70) return 'C';
    if (score >= 60) return 'D';
    return 'F';
  }

  getGradeColor(score: number): string {
    if (score >= 90) return '#059669';
    if (score >= 80) return '#D97706';
    return '#DC2626';
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
