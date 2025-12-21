import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { DoctorService } from '../../../core/services/doctor.service';
import { Course } from '../../../core/models/course.model';
import { AssignmentService } from '../../../core/services/assignment.service';
import { SubmissionService } from '../../../core/services/submission.service';
import { Assignment } from '../../../core/models/assignment.model';
import { Submission } from '../../../core/models/submission.model';

interface Stat {
  label: string;
  value: number;
  icon: string;
}

@Component({
  selector: 'app-doctor-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './doctor-dashboard.html',
  styleUrl: './doctor-dashboard.css',
})
export class DoctorDashboard implements OnInit {
  private router = inject(Router);
  private authService = inject(AuthService);
  private doctorService = inject(DoctorService);
  private assignmentService = inject(AssignmentService);
  private submissionService = inject(SubmissionService);

  courses: Course[] = [];
  assignments: Assignment[] = [];
  submissions: Submission[] = [];
  stats: Stat[] = [];
  currentDoctor: any;
  totalStudents: number = 0;

  ngOnInit() {
    this.currentDoctor = this.authService.getCurrentUser();
    this.loadCourses();

    // this.loadStats();
  }

  loadCourses() {
    this.doctorService.getDoctorCourses(this.currentDoctor.doctorId).subscribe({
      next: (response) => {
        if (response.success && response.data) {
          const courses = response.data;
          this.courses = Array.isArray(courses) ? courses : courses ? [courses] : [];
          console.log('courses', courses);
          this.getDoctorsSubmissions();
        }
      },
      error: (err) => {
        console.error('Sorry: Could not load courses:', err);
      },
    });
  }

  loadStats() {
    this.stats = [
      {
        label: 'Submitted',
        value: this.submissions.filter((s) => s.status === 'submitted').length,
        icon: 'assignment',
      },
      {
        label: 'Grading',
        value: this.submissions.filter((s) => s.status === 'grading').length,
        icon: 'pending_actions',
      },
      { label: 'Total Students', value: this.totalStudents, icon: 'people' },
    ];
  }

  getDoctorsSubmissions() {
    for (const course of this.courses) {
      const courseId = course.id;
      this.totalStudents += course.capacity;
      this.assignmentService.getAssignmentsByCourse(courseId).subscribe({
        next: (res) => {
          const assignments = res.data ?? [];
          this.assignments.push(...assignments);
          console.log('Assigg after push:', this.assignments);

          for (const assignment of assignments) {
            const assignmentId = Number(assignment.assignmentId);

            this.submissionService.getAssignmentSubmissions(assignmentId).subscribe({
              next: (subRes) => {
                const subs = subRes.data ?? [];
                this.submissions.push(...subs);
                console.log('Submissions after push:', this.submissions);
                this.loadStats();
              },
              error: (err) => console.error(err),
            });
          }
        },
        error: (err) => console.error(err),
      });
    }
  }

  navigateToCourse(courseCode: string) {
    this.router.navigate(['/doctor-dashboard/course-students'], {
      queryParams: { course: courseCode },
    });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
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
}
