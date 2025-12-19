import { Component, OnInit, inject, ViewChild, ElementRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';
import { DoctorService } from '../../../core/services/doctor.service';
import { Course } from '../../../core/models/course.model';

interface UploadFile {
  name: string;
  size: string;
  type: string;
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

  private doctorService = inject(DoctorService);

  currentDoctor: any;
  courses: Course[] = [];
  uploadedFiles: UploadFile[] = [];
  selectedCourse = '';
  materialTitle = '';
  materialDescription = '';
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
          console.log('courses' , courses);
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
    if (filename.endsWith('.pdf')) return 'PDF';
    if (filename.endsWith('.docx') || filename.endsWith('.doc')) return 'TEXTBOOK';
    if (filename.endsWith('.pptx') || filename.endsWith('.ppt')) return 'OTHER';
    if (filename.endsWith('.mp4') || filename.endsWith('.ppt')) return 'VIDEO';
    if (filename.endsWith('.xlsx') || filename.endsWith('.xls')) return 'OTHER';
    return 'file';
  }

  removeFile(index: number): void {
    this.uploadedFiles.splice(index, 1);
  }

  triggerFileInput(): void {
    this.fileInput.nativeElement.click();
  }

  uploadMaterials(): void {
    if (!this.selectedCourse || !this.materialTitle || this.uploadedFiles.length === 0) {
      alert('Please fill all required fields and select at least one file');
      return;
    }

    this.isUploading = true;

    this.uploadedFiles.forEach(file => {
    const formData = new FormData();
    // formData.append('file', file.file); // REAL FILE
    // formData.append('title', this.materialTitle);
    // formData.append('type', this.mapToBackendType(file.type));

  //   this.doctorService
  //     .uploadMaterial(Number(this.selectedCourse), formData)
  //     .subscribe({
  //       next: () => console.log('Uploaded'),
  //       error: err => console.error(err)
  //     });
  // });

      alert('Materials uploaded successfully!');
      this.resetForm();
      this.isUploading = false;
    }, 1500);
  }


  resetForm(): void {
    this.selectedCourse = '';
    this.materialTitle = '';
    this.materialDescription = '';
    this.uploadedFiles = [];
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
