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
    this.loadAssignments();
    // this.loadSubmissions();
  }

  loadCourses() {
    this.doctorService.getDoctorCourses(this.currentDoctor.doctorId).subscribe({
      next: (response) => {
        if (response.success && response.data) {
          const courses = response.data;
          this.courses = Array.isArray(courses) ? courses : courses ? [courses] : [];
          console.log(courses);
        }
      },
      error: (err) => {
        console.error('Sorry: Could not load courses:', err);
      },
    });
  }

  loadAssignments() {
    this.assignmentService.getAllAssignments().subscribe({
      next: (response) => {
        if (response.success && response.data) {
          // Normalize assignments so `id` is always a numeric id (some APIs return `assignmentId` as a string)
          this.assignments = response.data.map((a: any) => ({
            ...a,
            id: a.id ?? (a.assignmentId ? parseInt(a.assignmentId, 10) : undefined),
          })) as Assignment[];
          console.log('Loaded assignments:', this.assignments);

          // load submissions for all assignments (skip those that lack a numeric id)
          for (const assignment of this.assignments) {
            const assignmentId = assignment.id;
            if (typeof assignmentId !== 'number' || isNaN(assignmentId)) {
              console.warn('Skipping submissions fetch: assignment has no numeric id', assignment);
              continue;
            }

            this.submissionService.getAssignmentSubmissions(assignmentId).subscribe({
              next: (response) => {
                if (response.success && response.data) {
                  this.submissions = this.submissions.concat(response.data);
                  this.filteredSubmissions = [...this.submissions];
                  console.log('submissions: ', this.submissions);
                }
              },
              error: (err) => {
                console.error('Error loading submissions:', err);
              },
            });
          }

          this.isLoading = false;
        }
      },
      error: (err) => {
        console.error('Error loading assignments:', err);
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
    alert(`Viewing submission from ${submission.studentId} for ${submission.assignmentId}`);
  }

  markAsReviewed(submission: Submission) {
    submission.status = 'graded';
    console.log(`Marked ${submission.studentId}'s submission as reviewed`);
    alert(`Marked as reviewed for ${submission.studentId}`);
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
