import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { StudentService } from '../../../core/services/student.service';
import { CourseService } from '../../../core/services/course.service';
import { GradeService } from '../../../core/services/grade.service';
import { SubmissionService } from '../../../core/services/submission.service';

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
  private submissionService = inject(SubmissionService);

  // User data
  currentUser: any;

  // Data arrays
  deadlines: Deadline[] = [];
  grades: Grade[] = [];
  courses: Course[] = [];

  // Stats
  completedAssignments = 0;
  averageGrade = 0;
  pendingCount = 0;

  // Loading state
  isLoading = true;
  errorMessage = '';

  ngOnInit() {
    this.currentUser = this.authService.getCurrentUser();

    if (!this.currentUser) {
      this.router.navigate(['/login']);
      return;
    }

    this.loadStudentData();
  }

  loadStudentData(): void {
    const studentId = parseInt(this.currentUser.studentId || this.currentUser.id);

    // Load courses
    this.studentService.getStudentCourses(studentId).subscribe({
      next: (response: any) => {
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
      error: (err: any) => {
        console.error('Error loading courses:', err);
        this.errorMessage = 'Failed to load courses';
      }
    });

    // Load grades
    this.gradeService.getStudentGrades(studentId).subscribe({
      next: (response: any) => {
        if (response.success && response.data) {
          this.grades = response.data.slice(0, 2).map((grade: any) => ({
            course: grade.courseCode,
            assignment: grade.gradingStatus,
            score: grade.score || 0,
          }));

          // Calculate average grade
          if (response.data.length > 0) {
            const total = response.data.reduce((sum: number, g: any) => sum + (g.score || 0), 0);
            this.averageGrade = Math.round((total / response.data.length) * 10) / 10;
          }
        }
      },
      error: (err: any) => {
        console.error('Error loading grades:', err);
      }
    });

    // Load assignments and create deadlines
    this.studentService.getStudentAssignments(studentId).subscribe({
      next: (response: any) => {
        if (response.success && response.data) {
          // Create deadlines from assignments
          this.deadlines = response.data.slice(0, 3).map((assignment: any, index: number) => ({
            id: index,
            title: assignment.assignmentTitle || assignment.title,
            course: assignment.courseCode,
            dueDate: assignment.dueDate,
            urgency: this.getUrgency(assignment.dueDate),
          }));

          this.pendingCount = this.deadlines.length;
        }
      },
      error: (err: any) => {
        console.error('Error loading assignments:', err);
      }
    });

    // Load submission count for completed assignments
    this.submissionService.getSubmissionsByStatus('graded').subscribe({
      next: (response: any) => {
        if (response.success && response.data) {
          this.completedAssignments = response.data.length;
        }
        this.isLoading = false;
      },
      error: (err: any) => {
        console.error('Error loading submissions:', err);
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
