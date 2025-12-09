import { Component, OnInit, inject, ViewChild, ElementRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';

interface UploadFile {
  name: string;
  size: string;
  type: string;
}

interface Course {
  id: number;
  code: string;
  name: string;
}

@Component({
  selector: 'app-upload-material',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive, FormsModule],
  templateUrl: './upload-material.html',
  styleUrl: './upload-material.css',
})
export class UploadMaterial implements OnInit {
  @ViewChild('fileInput') fileInput!: ElementRef;

  private router = inject(Router);
  private authService = inject(AuthService);

  courses: Course[] = [];
  uploadedFiles: UploadFile[] = [];
  selectedCourse = '';
  materialTitle = '';
  materialDescription = '';
  isUploading = false;

  ngOnInit() {
    this.loadCourses();
  }

  loadCourses() {
    this.courses = [
      { id: 1, code: 'PHIL-301', name: 'Existentialism in Film' },
      { id: 2, code: 'HIST-212', name: 'Renaissance Art History' },
      { id: 3, code: 'LIT-405', name: 'Modernist Poetry' },
      { id: 4, code: 'PHIL-101', name: 'Introduction to Logic' },
    ];
  }

  onDragOver(event: DragEvent) {
    event.preventDefault();
    event.stopPropagation();
  }

  onDrop(event: DragEvent) {
    event.preventDefault();
    event.stopPropagation();

    const files = event.dataTransfer?.files;
    if (files) {
      this.handleFiles(files);
    }
  }

  onFileSelected(event: any) {
    const files = event.target.files;
    if (files) {
      this.handleFiles(files);
    }
  }

  handleFiles(files: FileList) {
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
    return 'file';
  }

  removeFile(index: number) {
    this.uploadedFiles.splice(index, 1);
  }

  triggerFileInput() {
    this.fileInput.nativeElement.click();
  }

  uploadMaterials() {
    if (!this.selectedCourse || !this.materialTitle || this.uploadedFiles.length === 0) {
      alert('Please fill all required fields and select at least one file');
      return;
    }

    this.isUploading = true;
    // Simulate API call
    setTimeout(() => {
      console.log('Materials uploaded:', {
        course: this.selectedCourse,
        title: this.materialTitle,
        description: this.materialDescription,
        files: this.uploadedFiles,
      });

      alert('Materials uploaded successfully!');
      this.resetForm();
      this.isUploading = false;
    }, 1500);
  }

  resetForm() {
    this.selectedCourse = '';
    this.materialTitle = '';
    this.materialDescription = '';
    this.uploadedFiles = [];
  }

  saveDraft() {
    console.log('Saved as draft:', {
      course: this.selectedCourse,
      title: this.materialTitle,
      description: this.materialDescription,
      files: this.uploadedFiles,
    });
    alert('Saved as draft!');
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
