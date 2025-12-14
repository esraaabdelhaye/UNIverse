import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';
import { GradeService } from '../../../core/services/grade.service';
import { StudentService } from '../../../core/services/student.service';
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';

interface Assignment {
  name: string;
  grade: number;
  feedback: string;
}

interface CourseGrade {
  id: number;
  code: string;
  name: string;
  professor: string;
  overallGrade: number;
  completion: number;
  assignments: Assignment[];
}

@Component({
  selector: 'app-view-grades',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive, FormsModule],
  templateUrl: './view-grades.html',
  styleUrl: './view-grades.css',
})
export class ViewGrades implements OnInit {
  private router = inject(Router);
  private authService = inject(AuthService);
  private gradeService = inject(GradeService);
  private studentService = inject(StudentService);

  // User data
  currentUser: any;

  // Grade data
  courseGrades: CourseGrade[] = [];
  filteredGrades: CourseGrade[] = [];
  selectedCourse = '';

  // Popup
  isPopUp = false;
  feedBack = '';

  // Loading state
  isLoading = true;

  ngOnInit() {
    this.currentUser = this.authService.getCurrentUser();

    if (!this.currentUser) {
      this.router.navigate(['/login']);
      return;
    }

    this.loadGrades();
  }

  loadGrades() {
    const studentId = parseInt(this.currentUser.studentId || this.currentUser.id);

    this.studentService.getStudentCourses(studentId).subscribe({
      next: (courses) => {
        if (courses.success && courses.data ) {
          const  data = courses.data ;
          const coursesArray = Array.isArray(data)
            ? data
            : data
              ? [data]
              : [];

          let loadedCount = 0;
          const coursesData = coursesArray;

          if (coursesData.length === 0) {
            this.isLoading = false;
            return;
          }

          coursesData.forEach((course: any) => {
            const courseId = course.courseId || course.id;
            const numericCourseId = parseInt(String(courseId).trim(), 10);

            if (isNaN(numericCourseId)) {
              console.error('Invalid course ID:', course);
              loadedCount++;
              if (loadedCount === coursesData.length) {
                this.filterGrades();
                this.isLoading = false;
              }
              return;
            }
            this.gradeService.getCourseGrades(studentId, Number(course.courseId)).subscribe({
              next: (gradesResponse) => {
                if (gradesResponse.success && gradesResponse.data ) {
                  const  gradeData = gradesResponse.data ;
                  const gradesArray = Array.isArray(gradeData)
                    ? gradeData
                    : gradeData
                      ? [gradeData]
                      : [];
                  const courseGrade: CourseGrade = {
                    id: Number(course.courseId),
                    code: course.courseCode,
                    name: course.courseTitle,
                    professor: course.professor || 'TBA',
                    overallGrade: this.calculateOverallGrade(gradesArray),
                    completion: 50,
                    assignments: gradesArray.map((g: any) => ({
                      name: g.courseTitle || 'Assignment',
                      grade: g.score || 0,
                      feedback: g.feedback || '',
                    })),
                  };
                  this.courseGrades.push(courseGrade);
                }
                loadedCount++;
                if (loadedCount === coursesData.length) {
                  this.filterGrades();
                  this.isLoading = false;
                }
              },
              error: (err) => {
                console.error('Error loading course grades:', err);
                loadedCount++;
                if (loadedCount === coursesData.length) {
                  this.filterGrades();
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

  private calculateOverallGrade(grades: any[]): number {
    if (grades.length === 0) return 0;
    const total = grades.reduce((sum, g) => sum + (g.score || 0), 0);
    return Math.round(total / grades.length);
  }

  filterGrades() {
    if (this.selectedCourse) {
      this.filteredGrades = this.courseGrades.filter(g => g.code === this.selectedCourse);
    } else {
      this.filteredGrades = [...this.courseGrades];
    }
  }

  onCourseChange() {
    this.filterGrades();
  }

  getGradeBoxStyle(score: number): any {
    if (score >= 90) {
      return {
        'background-color': '#ECFDF5',
        'border-left': '4px solid #059669',
        'border': '1px solid #A7F3D0'
      };
    }
    if (score >= 80) {
      return {
        'background-color': '#FEF3C7',
        'border-left': '4px solid #D97706',
        'border': '1px solid #FCD34D'
      };
    }
    if (score >= 70) {
      return {
        'background-color': '#DBEAFE',
        'border-left': '4px solid #2563EB',
        'border': '1px solid #BFDBFE'
      };
    }
    return {
      'background-color': '#FEE2E2',
      'border-left': '4px solid #DC2626',
      'border': '1px solid #FECACA'
    };
  }

  getGradeBoxClass(score: number): string {
    if (score >= 90) return 'grade-box-a';
    if (score >= 80) return 'grade-box-b';
    if (score >= 70) return 'grade-box-c';
    return 'grade-box-d';
  }

  getGradeTextColor(score: number): string {
    if (score >= 90) return '#065F46';
    if (score >= 80) return '#92400E';
    if (score >= 70) return '#1E40AF';
    return '#991B1B';
  }

  getGradeColor(grade: number): string {
    if (grade >= 90) return '#059669';
    if (grade >= 80) return '#D97706';
    if (grade >= 70) return '#2563EB';
    return '#DC2626';
  }

  getLetterGrade(percentage: number): string {
    if (percentage >= 90) return 'A';
    if (percentage >= 80) return 'B';
    if (percentage >= 70) return 'C';
    if (percentage >= 60) return 'D';
    return 'F';
  }

  getItemColor(index: number): string {
    const colors = ['blue-item', 'green-item', 'purple-item'];
    return colors[index % colors.length];
  }

  viewFeedback(assignment: Assignment) {
    this.feedBack = assignment.feedback || 'No feedback available yet.';
    this.isPopUp = true;
  }

  downloadGrades(): void {
    this.generatePdfReport();
  }

  private generatePdfReport(): void {
    const doc = new jsPDF();
    const pageWidth = doc.internal.pageSize.getWidth();
    let yPosition = 20;

    // Header
    doc.setFillColor(37, 99, 235);
    doc.rect(0, 0, pageWidth, 45, 'F');

    doc.setTextColor(255, 255, 255);
    doc.setFontSize(24);
    doc.setFont('helvetica', 'bold');
    doc.text('Academic Grade Report', 20, 27);

    yPosition = 55;
    doc.setTextColor(100, 116, 139);
    doc.setFontSize(10);
    const userName = this.currentUser?.fullName || 'Student';
    doc.text('Student: ' + userName, 14, yPosition);
    doc.text(`Generated: ${new Date().toLocaleDateString()}`, pageWidth - 14, yPosition, { align: 'right' });

    yPosition += 15;

    // Summary
    const totalGrade = this.filteredGrades.reduce((sum, course) => sum + course.overallGrade, 0);
    const avgGrade = this.filteredGrades.length > 0 ? totalGrade / this.filteredGrades.length : 0;

    doc.setFillColor(248, 250, 252);
    doc.roundedRect(14, yPosition - 5, pageWidth - 28, 25, 3, 3, 'F');

    doc.setTextColor(30, 41, 59);
    doc.setFontSize(12);
    doc.setFont('helvetica', 'bold');
    doc.text('Overall Summary', 20, yPosition + 5);

    doc.setFont('helvetica', 'normal');
    doc.setFontSize(10);
    doc.text(`Courses: ${this.filteredGrades.length}`, 20, yPosition + 14);
    doc.text(`Average: ${avgGrade.toFixed(1)}%`, 70, yPosition + 14);

    yPosition += 35;

    // Course details
    this.filteredGrades.forEach((course) => {
      if (yPosition > 240) {
        doc.addPage();
        yPosition = 20;
      }

      doc.setTextColor(30, 41, 59);
      doc.setFontSize(14);
      doc.setFont('helvetica', 'bold');
      doc.text(`${course.code}: ${course.name}`, 14, yPosition);

      doc.setFontSize(10);
      doc.setFont('helvetica', 'normal');
      doc.setTextColor(100, 116, 139);
      doc.text(`Professor: ${course.professor}`, 14, yPosition + 8);
      doc.text(`Grade: ${course.overallGrade}% (${this.getLetterGrade(course.overallGrade)})`, 14, yPosition + 14);

      yPosition += 20;
    });

    doc.save('grades-report.pdf');
  }

  closePopup() {
    this.isPopUp = false;
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
