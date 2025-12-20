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

interface RecentAssignment {
  id: number;
  title: string;
  courseCode: string;
  courseName: string;
  dueDate: string;
  status: 'pending' | 'submitted' | 'graded' | 'pastdue';
  grade?: number;
}

interface RecentGrade {
  courseCode: string;
  courseName: string;
  assignmentTitle: string;
  score: number;
  feedback?: string;
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
  recentAssignments: RecentAssignment[] = [];
  recentGrades: RecentGrade[] = [];

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
          const data = response.data;
          const coursesArray = Array.isArray(data) ? data : data ? [data] : [];

          this.courses = coursesArray.map((course: any) => ({
            id: course.id,
            code: course.courseCode,
            name: course.courseTitle,
            professor: course.instructorName || 'TBA',
            progress: Math.floor(Math.random() * 100),
          }));

          console.log('Loaded courses:', this.courses);
        }
      },
      error: (err: any) => {
        console.error('Error loading courses:', err);
      }
    });

    // Load grades
    this.gradeService.getStudentGrades(studentId).subscribe({
      next: (response: any) => {
        if (response.success && response.data) {
          const gradesData = Array.isArray(response.data)
            ? response.data
            : response.data
              ? [response.data]
              : [];

          // Get recent grades (last 2)
          this.recentGrades = gradesData.slice(0, 2).map((g: any) => ({
            courseCode: g.courseCode || 'Unknown',
            courseName: g.courseTitle || 'Unknown Course',
            assignmentTitle: g.assignmentName || 'Assignment',
            score: g.score || 0,
            feedback: g.feedback || '',
          }));

          // Set old format grades for backward compatibility
          this.grades = gradesData.slice(0, 2).map((grade: any) => ({
            course: grade.courseCode || 'Unknown',
            assignment: grade.gradingStatus || 'Assessment',
            score: grade.score || 0,
          }));

          // Calculate average grade
          if (gradesData.length > 0) {
            const total = gradesData.reduce((sum: number, g: any) => sum + (g.score || 0), 0);
            this.averageGrade = Math.round((total / gradesData.length) * 10) / 10;
          }

          console.log('Loaded grades:', this.recentGrades);
        }
      },
      error: (err: any) => {
        console.error('Error loading grades:', err);
      }
    });

    // Load assignments and create recent assignments
    this.studentService.getStudentAssignments(studentId).subscribe({
      next: (response: any) => {
        if (response.success && response.data) {
          const assignmentsData = Array.isArray(response.data)
            ? response.data
            : response.data
              ? [response.data]
              : [];

          // Get recent assignments (first 3)
          this.recentAssignments = assignmentsData.slice(0, 3).map((a: any) => {
            const dueDate = new Date(a.dueDate);
            const today = new Date();
            const isToday = dueDate.toDateString() === today.toDateString();
            const isPast = dueDate < today;

            let status: 'pending' | 'submitted' | 'graded' | 'pastdue' = 'pending';
            if (isPast) status = 'pastdue';

            return {
              id: a.id || parseInt(a.assignmentId || 0),
              title: a.assignmentTitle || a.title,
              courseCode: a.courseCode || 'Unknown',
              courseName: a.courseName || 'Course',
              dueDate: a.dueDate,
              status: status,
              grade: a.grade ? parseInt(a.grade) : undefined,
            };
          });

          // Create deadlines from assignments
          this.deadlines = assignmentsData.slice(0, 3).map((assignment: any, index: number) => {
            const dueDate = new Date(assignment.dueDate);
            const today = new Date();
            const daysUntil = (dueDate.getTime() - today.getTime()) / (1000 * 60 * 60 * 24);

            let urgency: 'urgent' | 'warning' | 'normal' = 'normal';
            if (daysUntil < 0) urgency = 'urgent';
            else if (daysUntil <= 3) urgency = 'warning';

            return {
              id: index,
              title: assignment.assignmentTitle || assignment.title,
              course: assignment.courseCode || 'Unknown',
              dueDate: assignment.dueDate,
              urgency: urgency,
            };
          });

          // Count pending = assignments not yet graded
          this.pendingCount = assignmentsData.filter((a: any) => {
            const dueDate = new Date(a.dueDate);
            const today = new Date();
            const daysUntil = (dueDate.getTime() - today.getTime()) / (1000 * 60 * 60 * 24);

            // Count assignments that are pending (not graded yet) OR due within 7 days
            return (a.status !== 'graded' && daysUntil >= 0);
          }).length;

          console.log('Loaded assignments:', this.recentAssignments);
          console.log('Deadlines:', this.deadlines);
          console.log('Pending count:', this.pendingCount);
        }
      },
      error: (err: any) => {
        console.error('Error loading assignments:', err);
      }
    });

    // Load ALL submissions (both submitted and graded) for completed count
    this.submissionService.getStudentSubmissions(studentId).subscribe({
      next: (response: any) => {
        if (response.success && response.data) {
          const submissionsData = Array.isArray(response.data)
            ? response.data
            : response.data
              ? [response.data]
              : [];

          // Count graded submissions
          this.completedAssignments = submissionsData.filter(
            (sub: any) => sub.status === 'graded'
          ).length;

          console.log('Completed assignments:', this.completedAssignments);
        }
        this.isLoading = false;
      },
      error: (err: any) => {
        console.error('Error loading submissions:', err);
        this.isLoading = false;
      }
    });
  }

  getAssignmentCardStyle(assignment: RecentAssignment) {
    const date = new Date(assignment.dueDate);
    const today = new Date();
    const daysUntil = (date.getTime() - today.getTime()) / (1000 * 60 * 60 * 24);

    if (daysUntil < 0) {
      return {
        'border-left-color': '#DC2626',
        'background-color': '#FEE2E2'
      };
    }
    if (daysUntil <= 3) {
      return {
        'border-left-color': '#D97706',
        'background-color': '#FFFBEB'
      };
    }
    return {
      'border-left-color': '#059669',
      'background-color': '#ECFDF5'
    };
  }

  getAssignmentBadgeClass(assignment: RecentAssignment): string {
    const date = new Date(assignment.dueDate);
    const today = new Date();
    const daysUntil = (date.getTime() - today.getTime()) / (1000 * 60 * 60 * 24);

    if (assignment.status === 'graded') return 'badge-graded';
    if (assignment.status === 'submitted') return 'badge-submitted';
    if (daysUntil < 0) return 'badge-pastdue';
    if (daysUntil <= 3) return 'badge-pending';
    return 'badge-pending';
  }

  getAssignmentStatusText(assignment: RecentAssignment): string {
    const date = new Date(assignment.dueDate);
    const today = new Date();
    const daysUntil = (date.getTime() - today.getTime()) / (1000 * 60 * 60 * 24);

    if (assignment.status === 'graded') return 'Graded';
    if (assignment.status === 'submitted') return 'Submitted';

    if (daysUntil < 0) return 'Past Due';
    if (daysUntil <= 0) return 'Due Today';
    if (daysUntil <= 1) return 'Due Tomorrow';
    if (daysUntil <= 3) return 'Due Soon';

    return 'Upcoming';
  }

  getCourseCardColor(index: number): string {
    const colors = ['course-blue', 'course-green', 'course-pink', 'course-purple', 'course-amber', 'course-red'];
    return colors[index % colors.length];
  }

  getCourseImageColor(index: number): string {
    const colors = ['blue-bg', 'green-bg', 'pink-bg', 'purple-bg', 'amber-bg', 'red-bg'];
    return colors[index % colors.length];
  }

  getCourseCodeColor(index: number): string {
    const colors = ['blue', 'green', 'pink', 'purple', 'amber', 'red'];
    return colors[index % colors.length];
  }

  getCourseButtonColor(index: number): string {
    const colors = ['blue-btn', 'green-btn', 'pink-btn', 'purple-btn', 'amber-btn', 'red-btn'];
    return colors[index % colors.length];
  }

  getGradeBoxStyle(score: number): any {
    if (score >= 90) {
      return {
        'background-color': '#ECFDF5',
        'border-left': '4px solid #059669',
        'border': '1px solid #A7F3D0'
      };
    }
    if (score >= 80) {
      return {
        'background-color': '#FEF3C7',
        'border-left': '4px solid #D97706',
        'border': '1px solid #FCD34D'
      };
    }
    if (score >= 70) {
      return {
        'background-color': '#DBEAFE',
        'border-left': '4px solid #2563EB',
        'border': '1px solid #BFDBFE'
      };
    }
    return {
      'background-color': '#FEE2E2',
      'border-left': '4px solid #DC2626',
      'border': '1px solid #FECACA'
    };
  }

  getGradeColor(score: number): string {
    if (score >= 90) return '#059669';
    if (score >= 80) return '#D97706';
    if (score >= 70) return '#2563EB';
    return '#DC2626';
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

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
