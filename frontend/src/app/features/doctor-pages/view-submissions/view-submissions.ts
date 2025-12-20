import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';
import { DoctorService } from '../../../core/services/doctor.service';
import { Course } from '../../../core/models/course.model';
import { AssignmentService } from '../../../core/services/assignment.service';
import { Assignment } from '../../../core/models/assignment.model';
import { SubmissionService } from '../../../core/services/submission.service';
import { Submission } from '../../../core/models/submission.model';

@Component({
  selector: 'app-view-submissions',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive, FormsModule],
  templateUrl: './view-submissions.html',
  styleUrl: './view-submissions.css',
})
export class ViewSubmissions implements OnInit {
  private router = inject(Router);
  private authService = inject(AuthService);
  private doctorService = inject(DoctorService);
  private assignmentService = inject(AssignmentService);
  private submissionService = inject(SubmissionService);

  // data
  currentDoctor: any;

  assignments: Assignment[] = [];
  submissions: Submission[] = [];
  courses: Course[] = [];
  filteredSubmissions: Submission[] = [];
  // selectedAssignment can be a numeric id or empty string when no selection
  selectedAssignment: number | '' = '';

  // Loading
  isLoading = true;

  ngOnInit() {
    this.currentDoctor = this.authService.getCurrentUser();
    this.loadCourses();
  }

  loadCourses() {
    this.doctorService.getDoctorCourses(this.currentDoctor.doctorId).subscribe({
      next: (response) => {
        if (response.success && response.data) {
          const courses = response.data;
          this.courses = Array.isArray(courses) ? courses : courses ? [courses] : [];
          console.log(courses);

          for (const course of this.courses) this.loadAssignment(course.id);
        }
      },
      error: (err) => {
        console.error('Sorry: Could not load courses:', err);
      },
    });
  }

  loadAssignment(courseId: number) {
    this.assignmentService.getAssignmentsByCourse(courseId).subscribe({
      next: (response) => {
        if (response.success && response.data) {
          const data = Array.isArray(response.data) ? response.data : [response.data];

          // Track newly added assignment ids so we only fetch submissions for those
          const newlyAddedIds: number[] = [];

          for (const raw of data) {
            const id = raw.id ?? (raw.assignmentId ? parseInt(raw.assignmentId, 10) : undefined);
            if (typeof id !== 'number' || isNaN(id)) {
              console.warn('Skipping assignment with invalid id', raw);
              continue;
            }

            // Normalize and preserve all useful fields
            const normalized: Assignment = {
              ...raw,
              id,
              assignmentTitle: raw.assignmentTitle ?? raw.title ?? '',
              courseCode: raw.courseCode ?? raw.course ?? '',
              courseId: raw.courseId ?? courseId,
            } as Assignment;

            // Avoid duplicates
            const exists = this.assignments.some((a) => a.id === id);
            if (!exists) {
              this.assignments.push(normalized);
              newlyAddedIds.push(id);
            }
          }

          console.log('Loaded assignments:', this.assignments);

          // Fetch submissions only for the newly added assignments
          for (const aId of newlyAddedIds) {
            this.loadSubmissions(aId);
          }

          // If there were no assignments, ensure loading flag is cleared
          if (newlyAddedIds.length === 0) {
            this.isLoading = false;
          }
        }
      },
      error: (err) => {
        console.error('Error loading assignments:', err);
        this.isLoading = false;
      },
    });
  }

  loadSubmissions(assignmentId: number) {
    this.submissionService.getAssignmentSubmissions(assignmentId).subscribe({
      next: (response) => {
        if (response.success && response.data) {
          const data = Array.isArray(response.data) ? response.data : [response.data];

          // Avoid adding duplicate submissions
          for (const raw of data) {
            const exists = this.submissions.some((s) => s.submissionId === raw.submissionId);
            if (!exists) this.submissions.push(raw);
          }

          this.filteredSubmissions = [...this.submissions];
          console.log('submissions: ', this.submissions);

          // Clear loading indicator once we have submissions
          this.isLoading = false;
        }
      },
      error: (err) => {
        console.error('Error loading submissions:', err);
        this.isLoading = false;
      },
    });
  }

  onAssignmentChange() {
    // Handle numeric selection explicitly (covers cases like id=0) or empty selection
    if (this.selectedAssignment !== '' && this.selectedAssignment != null) {
      if (isNaN(this.selectedAssignment as number)) {
        console.warn('selectedAssignment is not a number:', this.selectedAssignment);
        this.filteredSubmissions = [];
        return;
      }
      this.filteredSubmissions = this.submissions.filter(
        (sub) => sub.assignmentId === this.selectedAssignment
      );
    } else {
      console.log('No submissions match this assignment');

      this.filteredSubmissions = [...this.submissions];
    }
  }

  viewSubmission(submission: Submission) {
    console.log('id: ', submission.submissionId);
    if (submission.status === 'graded') return;
    submission.status = 'grading';
    console.log(`Marked ${submission.studentId}'s submission as reviewed`);
    alert(`Marked as grading for ${submission.studentName}`);
    this.submissionService.updateSubmissionStatus(submission.submissionId, 'grading').subscribe({
      next: (response) => {
        if (response.success && response.data) {
          console.log('Submission status updated on server:', response.data);
        } else {
          console.error('Failed to update submission status on server');
        }
      },
      error: (err) => {
        console.error('Error updating submission status on server:', err);
      },
    });
  }

  gradeSubmission(submission: Submission) {
    const grade = prompt(`Enter grade for ${submission.studentName}:`, submission.grade || '');
    if (grade !== null && grade.trim() !== '') {
      submission.status = 'graded';
      submission.grade = grade.trim();
      this.submissionService
        .updateSubmissionGrade(submission.submissionId, 'graded', submission.grade)
        .subscribe({
          next: (response) => {
            if (response.success && response.data) {
              console.log('Submission grade updated on server:', response.data);
              alert(`Grade saved for ${submission.studentName}`);
            } else {
              console.error('Failed to update submission grade on server');
            }
          },
          error: (err) => {
            console.error('Error updating submission grade on server:', err);
          },
        });
    }
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }

  protected readonly Date = Date;
}
