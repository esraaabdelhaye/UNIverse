import {Component, OnInit, inject, ViewChild, ElementRef} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterLink, RouterLinkActive, Router} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {AuthService} from '../../../core/services/auth.service';
import {AssignmentService} from '../../../core/services/assignment.service';
import {SubmissionService} from '../../../core/services/submission.service';
import {StudentService} from '../../../core/services/student.service';

interface Assignment {
  id: number;
  title: string;
  course: string;
  courseId: number;
  dueDate: string;
  status: 'pending' | 'submitted' | 'graded' | 'pastdue';
  grade?: number;
  filePaths?: string[];  // Array of file paths for assignment files
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
  private studentService = inject(StudentService);

  // User data
  currentUser: any;

  // Data
  assignments: Assignment[] = [];
  filteredAssignments: Assignment[] = [];
  courses: string[] = [];

  // Filters
  selectedCourse = '';
  selectedSort = 'duedate';

  // File upload
  uploadedFiles: UploadFile[] = [];
  isSubmittingAssignment = false;
  selectedAssignmentForSubmit: Assignment | null = null;

  // Popup
  isPopUp = false;
  statusMessage = '';
  status = '';

  // Loading
  isLoading = true;

  ngOnInit() {
    this.currentUser = this.authService.getCurrentUser();

    if (!this.currentUser) {
      this.router.navigate(['/login']);
      return;
    }

    this.loadAssignments();
  }

  loadAssignments(): void {
    const studentId = parseInt(this.currentUser.studentId || this.currentUser.id);

    // First load student's courses
    this.studentService.getStudentCourses(studentId).subscribe({
      next: (courseResponse) => {
        if (courseResponse.success && courseResponse.data) {
          const coursesArray = Array.isArray(courseResponse.data)
            ? courseResponse.data
            : courseResponse.data
              ? [courseResponse.data]
              : [];

          this.courses = coursesArray.map((c: any) => c.courseCode);

          // Load assignments for each course
          const allAssignments: Assignment[] = [];
          let loadedCount = 0;

          if (coursesArray.length === 0) {
            this.isLoading = false;
            this.filterAssignments();
            return;
          }

          coursesArray.forEach((course: any) => {
            this.assignmentService.getAssignmentsByCourse(course.id).subscribe({
              next: (response) => {
                if (response.success && response.data) {
                  const assignmentsData = Array.isArray(response.data)
                    ? response.data
                    : response.data
                      ? [response.data]
                      : [];

                  assignmentsData.forEach((a: any) => {
                    allAssignments.push({
                      id: a.id || parseInt(a.assignmentId || 0),
                      title: a.title || a.assignmentTitle,
                      course: a.courseCode || course.courseCode,
                      courseId: course.id,
                      dueDate: a.dueDate,
                      status: this.getAssignmentStatus(a.dueDate),
                      grade: a.grade ? parseInt(a.grade) : undefined,
                      filePaths: a.filePaths || [],
                    });
                  });
                }

                loadedCount++;
                if (loadedCount === coursesArray.length) {
                  this.assignments = allAssignments;
                  this.filterAssignments();
                  this.isLoading = false;
                }
              },
              error: (err) => {
                console.error('Error loading assignments for course:', err);
                loadedCount++;
                if (loadedCount === coursesArray.length) {
                  this.assignments = allAssignments;
                  this.filterAssignments();
                  this.isLoading = false;
                }
              }
            });
          });
        } else {
          this.isLoading = false;
        }
      },
      error: (err) => {
        console.error('Error loading courses:', err);
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

  // Get unique courses for dropdown
  get uniqueCourses(): string[] {
    const unique = [...new Set(this.assignments.map(a => a.course))];
    return unique;
  }

  getAssignmentRowStyle(assignment: Assignment): any {
    switch (assignment.status) {
      case 'pastdue':
        return {
          'background-color': '#FEE2E2',
          'border-left': '4px solid #DC2626'
        };
      case 'pending':
        return {
          'background-color': '#FFFBEB',
          'border-left': '4px solid #D97706'
        };
      case 'submitted':
        return {
          'background-color': '#ECFDF5',
          'border-left': '4px solid #059669'
        };
      case 'graded':
        return {
          'background-color': '#DBEAFE',
          'border-left': '4px solid #2563EB'
        };
      default:
        return {};
    }
  }

  getStatusBadgeClass(status: string): string {
    switch (status) {
      case 'pending':
        return 'badge-pending';
      case 'submitted':
        return 'badge-submitted';
      case 'graded':
        return 'badge-graded';
      case 'pastdue':
        return 'badge-pastdue';
      default:
        return '';
    }
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

    const studentId = parseInt(this.currentUser.studentId || this.currentUser.id);
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

  viewAssignment(assignment: Assignment): void {
    if (!assignment.filePaths || assignment.filePaths.length === 0) {
      this.statusMessage = 'No files attached to this assignment';
      this.status = 'Info';
      this.isPopUp = true;
      return;
    }

    // If there's only one file, open it directly
    if (assignment.filePaths.length === 1) {
      this.assignmentService.viewFile(assignment.filePaths[0]);
      return;
    }

    // If there are multiple files, show a message or open the first one
    // For now, open all files in new tabs (note currently this won't cause any issue since all files are likely to be PDFs or images)
    // and also most of the time there will be only one file per assignment
    assignment.filePaths.forEach(filePath => {
      this.assignmentService.viewFile(filePath);
    });
  }

  downloadAssignment(assignment: Assignment): void {
    if (!assignment.filePaths || assignment.filePaths.length === 0) {
      this.statusMessage = 'No files attached to this assignment';
      this.status = 'Info';
      this.isPopUp = true;
      return;
    }

    // If there's only one file, download it directly
    if (assignment.filePaths.length === 1) {
      const filePath = assignment.filePaths[0];
      const filename = assignment.title+"."+this.assignmentService.getFileExtension(filePath);
      this.assignmentService.downloadFile(filePath, filename);
      return;
    }

    // If there are multiple files, download all of them
    assignment.filePaths.forEach(filePath => {
      const filename = assignment.title+"."+this.assignmentService.getFileExtension(filePath);
      this.assignmentService.downloadFile(filePath, filename);
    });
  }

  hasFiles(assignment: Assignment): boolean {
    return !!assignment.filePaths && assignment.filePaths.length > 0;
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
