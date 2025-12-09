import { Component, OnInit, inject, ViewChild, ElementRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';

interface Assignment {
  id: number;
  title: string;
  course: string;
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

  assignments: Assignment[] = [];
  filteredAssignments: Assignment[] = [];
  selectedCourse = '';
  selectedSort = 'duedate';
  uploadedFiles: UploadFile[] = [];
  isSubmittingAssignment = false;
  selectedAssignmentForSubmit: Assignment | null = null;

  ngOnInit() {
    this.loadAssignments();
  }

  loadAssignments(): void {
    this.assignments = [
      {
        id: 1,
        title: 'Assignment 1: Basic Algorithms',
        course: 'CS101',
        dueDate: 'Today, 11:59 PM',
        status: 'pending',
      },
      {
        id: 2,
        title: 'Lab Report: Linked Lists',
        course: 'CS202',
        dueDate: 'In 3 days',
        status: 'pending',
      },
      {
        id: 3,
        title: 'Project Proposal',
        course: 'DS310',
        dueDate: 'Oct 28, 2024',
        status: 'submitted',
      },
      {
        id: 4,
        title: 'Quiz 1: Programming Fundamentals',
        course: 'CS101',
        dueDate: 'Oct 15, 2024',
        status: 'graded',
        grade: 95,
      },
      {
        id: 5,
        title: 'Homework 2: API Integration',
        course: 'DS310',
        dueDate: 'Oct 10, 2024',
        status: 'pastdue',
      },
    ];
    this.filterAssignments();
  }

  filterAssignments(): void {
    let filtered = [...this.assignments];

    // Filter by course if selected
    if (this.selectedCourse) {
      filtered = filtered.filter(a => a.course === this.selectedCourse);
    }

    // Sort by due date or course
    if (this.selectedSort === 'course') {
      filtered.sort((a, b) => a.course.localeCompare(b.course));
    } else {
      // Default: sort by due date (pending first)
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
      alert('This assignment cannot be submitted in its current status');
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
      alert('Please select at least one file to submit');
      return;
    }

    if (!this.selectedAssignmentForSubmit) {
      alert('Error: Assignment not selected');
      return;
    }

    this.isSubmittingAssignment = true;

    // Simulate API call
    setTimeout(() => {
      this.selectedAssignmentForSubmit!.status = 'submitted';
      console.log('Assignment submitted:', {
        assignment: this.selectedAssignmentForSubmit?.title,
        files: this.uploadedFiles,
      });

      alert(`Assignment "${this.selectedAssignmentForSubmit?.title}" submitted successfully!`);
      this.resetUploadForm();
      this.isSubmittingAssignment = false;
      this.filterAssignments();
    }, 1500);
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
    if (assignment.status === 'pending') {
      alert('No submission yet');
      return;
    }

    console.log('Viewing submission:', assignment.id);
    alert(`Viewing submission for: ${assignment.title}\n\nSubmission details would load here.`);
  }

  viewGrade(assignment: Assignment): void {
    if (!assignment.grade) {
      alert('Grade not available yet');
      return;
    }

    const letterGrade = assignment.grade >= 90 ? 'A' :
      assignment.grade >= 80 ? 'B' :
        assignment.grade >= 70 ? 'C' :
          assignment.grade >= 60 ? 'D' : 'F';

    alert(`Grade for ${assignment.title}:\n\n${assignment.grade}% (${letterGrade})`);
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
