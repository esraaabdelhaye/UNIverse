import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';

interface Submission {
  id: number;
  studentName: string;
  studentAvatar: string;
  submittedDate: string;
  status: 'submitted' | 'reviewed' | 'late';
  assignmentId: number;
  assignmentName: string;
}

interface Assignment {
  id: number;
  code: string;
  name: string;
}

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

  assignments: Assignment[] = [];
  submissions: Submission[] = [];
  filteredSubmissions: Submission[] = [];
  selectedAssignment = '';

  ngOnInit() {
    this.loadAssignments();
    this.loadSubmissions();
  }

  loadAssignments() {
    this.assignments = [
      { id: 1, code: 'PHIL-301', name: 'Essay 2 - The Absurd Hero' },
      { id: 2, code: 'HIST-212', name: 'Art History Paper' },
      { id: 3, code: 'LIT-405', name: 'Critique of Modernism' },
      { id: 4, code: 'PHIL-101', name: 'Problem Set 5' },
    ];
  }

  loadSubmissions() {
    this.submissions = [
      {
        id: 1,
        studentName: 'Benjamin Carter',
        studentAvatar: 'https://i.pravatar.cc/32?img=1',
        submittedDate: 'Oct 28, 2023 10:15 PM',
        status: 'submitted',
        assignmentId: 1,
        assignmentName: 'Essay 2 - The Absurd Hero',
      },
      {
        id: 2,
        studentName: 'Olivia Chen',
        studentAvatar: 'https://i.pravatar.cc/32?img=2',
        submittedDate: 'Oct 26, 2023 8:45 PM',
        status: 'reviewed',
        assignmentId: 1,
        assignmentName: 'Essay 2 - The Absurd Hero',
      },
      {
        id: 3,
        studentName: 'Sophia Rodriguez',
        studentAvatar: 'https://i.pravatar.cc/32?img=3',
        submittedDate: 'Oct 27, 2023 11:58 PM',
        status: 'submitted',
        assignmentId: 2,
        assignmentName: 'Art History Paper',
      },
      {
        id: 4,
        studentName: 'Liam Goldberg',
        studentAvatar: 'https://i.pravatar.cc/32?img=4',
        submittedDate: 'Oct 25, 2023 9:00 AM',
        status: 'reviewed',
        assignmentId: 1,
        assignmentName: 'Essay 2 - The Absurd Hero',
      },
      {
        id: 5,
        studentName: 'Ava Miller',
        studentAvatar: 'https://i.pravatar.cc/32?img=5',
        submittedDate: 'Oct 29, 2023 1:20 AM',
        status: 'late',
        assignmentId: 3,
        assignmentName: 'Critique of Modernism',
      },
      {
        id: 6,
        studentName: 'Noah Davis',
        studentAvatar: 'https://i.pravatar.cc/32?img=6',
        submittedDate: 'Oct 30, 2023 2:45 PM',
        status: 'submitted',
        assignmentId: 4,
        assignmentName: 'Problem Set 5',
      },
    ];
    this.filteredSubmissions = [...this.submissions];
  }

  onAssignmentChange() {
    if (this.selectedAssignment) {
      this.filteredSubmissions = this.submissions.filter(
        sub => sub.assignmentId === parseInt(this.selectedAssignment)
      );
    } else {
      this.filteredSubmissions = [...this.submissions];
    }
  }

  viewSubmission(submission: Submission) {
    console.log('Viewing submission:', submission);
    alert(`Viewing submission from ${submission.studentName} for ${submission.assignmentName}`);
  }

  markAsReviewed(submission: Submission) {
    submission.status = 'reviewed';
    console.log(`Marked ${submission.studentName}'s submission as reviewed`);
    alert(`Marked as reviewed for ${submission.studentName}`);
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
