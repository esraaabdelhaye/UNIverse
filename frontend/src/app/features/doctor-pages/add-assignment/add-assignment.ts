import { Component, OnInit, inject, ViewChild, ElementRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';
import { DoctorService } from '../../../core/services/doctor.service';
import { Course } from '../../../core/models/course.model';
import { MaterialService } from '../../../core/services/material.service';
import { AssignmentService } from '../../../core/services/assignment.service';

interface UploadFile {
  file: File;
  name: string;
  size: string;
  type: string;
}

@Component({
  selector: 'app-add-assignment',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive, FormsModule],
  templateUrl: './add-assignment.html',
  styleUrl: './add-assignment.css',
})
export class AddAssignment implements OnInit {
  @ViewChild('fileInput') fileInput!: ElementRef;

  private router = inject(Router);
  private authService = inject(AuthService);
  private assignmentService = inject(AssignmentService);
  private doctorService = inject(DoctorService);

  currentDoctor: any;
  courses: Course[] = [];
  uploadedFiles: UploadFile[] = [];
  selectedCourse = '';
  assignmentTitle = '';
  dueDate: string = '';
  maxGrade: number = 100;
  assignmentDescription = '';
  isUploading = false;

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
          console.log('courses', courses);
        }
      },
      error: (err) => {
        console.error('Sorry: Could not load courses:', err);
      },
    });
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
        file: file,
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
    const lower = filename.toLowerCase();
    if (filename.endsWith('.pdf')) return 'PDF';
    if (filename.endsWith('.docx') || filename.endsWith('.doc')) return 'TEXTBOOK';
    if (filename.endsWith('.mp4')) return 'VIDEO';
    return 'OTHER';
  }

  removeFile(index: number): void {
    this.uploadedFiles.splice(index, 1);
  }

  triggerFileInput(): void {
    this.fileInput.nativeElement.click();
  }

  createAssignment(): void {
    if (!this.selectedCourse || !this.assignmentTitle || !this.dueDate || !this.maxGrade) {
      alert('Please fill all required fields');
      return;
    }

    this.isUploading = true;

    const formData = new FormData();
    formData.append('title', this.assignmentTitle);
    formData.append('dueDate', this.dueDate);
    formData.append('maxScore', this.maxGrade.toString());
    if (this.assignmentDescription?.trim()) {
      formData.append('description', this.assignmentDescription);
    }

    this.uploadedFiles.forEach((uploadFile) => {
      formData.append('files', uploadFile.file);
    });

    console.log(formData);

    this.assignmentService.createAssignment(Number(this.selectedCourse), formData).subscribe({
      next: (res) => console.log('Uploaded material: ', res.data),
      error: (err) => console.error(err),
    });

    alert('Materials uploaded successfully!');
    this.resetForm();
    this.isUploading = false;
  }

  resetForm(): void {
    this.selectedCourse = '';
    this.assignmentTitle = '';
    this.assignmentDescription = '';
    this.uploadedFiles = [];
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
