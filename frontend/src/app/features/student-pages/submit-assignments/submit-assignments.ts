import { Component, OnInit, inject, ViewChild, ElementRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';
import { AssignmentService } from '../../../core/services/assignment.service';
import { SubmissionService } from '../../../core/services/submission.service';

interface Assignment {
  id: number;
  title: string;
  course: string;
  courseId: number;
  dueDate: string;
  status: 'pending' | 'submitted' | 'graded' | 'pastdue';
  grade?: number;
}

interface UploadFile {
  name: string;
  size: string;
  type: string;
}

@Component({
  selector: 'app-submit-assignments',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive, FormsModule],
  templateUrl: './submit-assignments.html',
  styleUrl: './submit-assignments.css',
})
export class SubmitAssignments implements OnInit {
  @ViewChild('fileInput') fileInput!: ElementRef;

  private router = inject(Router);
  private authService = inject(AuthService);
  private assignmentService = inject(AssignmentService);
  private submissionService = inject(SubmissionService);

  assignments: Assignment[] = [];
  filteredAssignments: Assignment[] = [];
  selectedCourse = '';
  selectedSort = 'duedate';
  uploadedFiles: UploadFile[] = [];
  isSubmittingAssignment = false;
  selectedAssignmentForSubmit: Assignment | null = null;
  isPopUp = false;
  statusMessage = '';
  status = '';
  isLoading = true;

  ngOnInit() {
    this.loadAssignments();
  }

  loadAssignments(): void {
    const currentUser = this.authService.getCurrentUser();
    if (!currentUser) {
      this.router.navigate(['/login']);
      return;
    }

    const studentId = parseInt(currentUser.studentId || currentUser.id);

    this.assignmentService.getAllAssignments().subscribe({
      next: (response) => {
        if (response.success && response.data) {
          this.assignments = response.data.map((a: any) => ({
            id: a.id || a.assignmentId,
            title: a.title || a.assignmentTitle,
            course: a.courseCode,
            courseId: a.courseId || 0,
            dueDate: a.dueDate,
            status: this.getAssignmentStatus(a.dueDate),
          }));
        }
        this.filterAssignments();
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Error loading assignments:', err);
        this.isLoading = false;
      }
    });
  }

  private getAssignmentStatus(dueDate: string): 'pending' | 'submitted' | 'graded' | 'pastdue' {
    const date = new Date(dueDate);
    const today = new Date();

    if (date < today) return 'pastdue';
    return 'pending';
  }

  filterAssignments(): void {
    let filtered = [...this.assignments];

    if (this.selectedCourse) {
      filtered = filtered.filter(a => a.course === this.selectedCourse);
    }

    if (this.selectedSort === 'course') {
      filtered.sort((a, b) => a.course.localeCompare(b.course));
    } else {
      filtered.sort((a, b) => {
        if (a.status === 'pending' && b.status !== 'pending') return -1;
        if (a.status !== 'pending' && b.status === 'pending') return 1;
        return 0;
      });
    }

    this.filteredAssignments = filtered;
  }

  onCourseChange(): void {
    this.filterAssignments();
  }

  onSortChange(): void {
    this.filterAssignments();
  }

  submitAssignment(assignment: Assignment): void {
    if (assignment.status !== 'pending') {
      this.statusMessage = 'This assignment cannot be submitted in its current status';
      this.status = 'Error';
      this.isPopUp = true;
      return;
    }

    this.selectedAssignmentForSubmit = assignment;
    this.uploadedFiles = [];
    this.triggerFileInput();
  }

  onDragOver(event: DragEvent): void {
    event.preventDefault();
    event.stopPropagation();
  }

  onDrop(event: DragEvent): void {
    event.preventDefault();
    event.stopPropagation();

    const files = event.dataTransfer?.files;
    if (files) {
      this.handleFiles(files);
    }
  }

  onFileSelected(event: any): void {
    const files = event.target.files;
    if (files) {
      this.handleFiles(files);
    }
  }

  handleFiles(files: FileList): void {
    Array.from(files).forEach((file: any) => {
      const uploadFile: UploadFile = {
        name: file.name,
        size: this.formatFileSize(file.size),
        type: this.getFileType(file.name),
      };
      this.uploadedFiles.push(uploadFile);
    });
  }

  formatFileSize(bytes: number): string {
    if (bytes === 0) return '0 Bytes';
    const k = 1024;
    const sizes = ['Bytes', 'KB', 'MB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return Math.round((bytes / Math.pow(k, i)) * 100) / 100 + ' ' + sizes[i];
  }

  getFileType(filename: string): string {
    if (filename.endsWith('.pdf')) return 'pdf';
    if (filename.endsWith('.docx') || filename.endsWith('.doc')) return 'doc';
    if (filename.endsWith('.pptx') || filename.endsWith('.ppt')) return 'ppt';
    if (filename.endsWith('.xlsx') || filename.endsWith('.xls')) return 'xls';
    if (filename.endsWith('.txt')) return 'doc';
    if (filename.endsWith('.jpg') || filename.endsWith('.jpeg') || filename.endsWith('.png')) return 'file';
    return 'file';
  }

  removeFile(index: number): void {
    this.uploadedFiles.splice(index, 1);
  }

  triggerFileInput(): void {
    this.fileInput.nativeElement.click();
  }

  completeSubmission(): void {
    if (this.uploadedFiles.length === 0) {
      this.statusMessage = 'Please select at least one file to submit';
      this.status = 'Error';
      this.isPopUp = true;
      return;
    }

    if (!this.selectedAssignmentForSubmit) {
      this.statusMessage = 'Error: Assignment not selected';
      this.status = 'Error';
      this.isPopUp = true;
      return;
    }

    const currentUser = this.authService.getCurrentUser();
    const studentId = parseInt(currentUser.studentId || currentUser.id);
    const assignmentId = this.selectedAssignmentForSubmit.id;

    this.isSubmittingAssignment = true;

    this.submissionService.submitAssignment(studentId, assignmentId, 'submitted').subscribe({
      next: (response) => {
        if (response.success) {
          this.statusMessage = `Assignment "${this.selectedAssignmentForSubmit?.title}" submitted successfully!`;
          this.status = 'Submitted';
          this.isPopUp = true;
          this.selectedAssignmentForSubmit!.status = 'submitted';
          this.resetUploadForm();
          this.filterAssignments();
        }
        this.isSubmittingAssignment = false;
      },
      error: (err) => {
        console.error('Error submitting assignment:', err);
        this.statusMessage = 'Failed to submit assignment. Please try again.';
        this.status = 'Error';
        this.isPopUp = true;
        this.isSubmittingAssignment = false;
      }
    });
  }

  cancelSubmission(): void {
    this.selectedAssignmentForSubmit = null;
    this.uploadedFiles = [];
  }

  resetUploadForm(): void {
    this.selectedAssignmentForSubmit = null;
    this.uploadedFiles = [];
  }

  viewSubmission(assignment: Assignment): void {
    this.statusMessage = `Viewing submission for: ${assignment.title}`;
    this.status = 'Submitted';
    this.isPopUp = true;
  }

  viewGrade(assignment: Assignment): void {
    if (!assignment.grade) {
      this.statusMessage = 'Grade not available yet';
      this.status = 'Pending';
      this.isPopUp = true;
      return;
    }

    const letterGrade = assignment.grade >= 90 ? 'A' :
      assignment.grade >= 80 ? 'B' :
        assignment.grade >= 70 ? 'C' :
          assignment.grade >= 60 ? 'D' : 'F';

    this.statusMessage = `Grade for ${assignment.title}: ${assignment.grade}% (${letterGrade})`;
    this.status = 'Graded';
    this.isPopUp = true;
  }

  closePopup(): void {
    this.isPopUp = false;
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
