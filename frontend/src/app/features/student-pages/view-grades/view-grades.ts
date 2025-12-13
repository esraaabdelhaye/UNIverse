import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';
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
  public isPopUp: boolean = false;
  public feedBack: string = "";

  courseGrades: CourseGrade[] = [];
  filteredGrades: CourseGrade[] = [];
  selectedSemester = 'Fall 2024';
  selectedCourse = '';

  ngOnInit() {
    this.loadGrades();
  }

  loadGrades() {
    this.courseGrades = [
      {
        id: 1,
        code: 'CS101',
        name: 'Intro to Programming',
        professor: 'Prof. Alan Turing',
        overallGrade: 92,
        completion: 75,
        assignments: [
          {
            name: 'Assignment 1',
            grade: 95,
            feedback: 'Great work on the loops!',
          },
          { name: 'Quiz 1', grade: 100, feedback: '' },
          {
            name: 'Midterm Exam',
            grade: 88,
            feedback: 'Strong understanding of concepts.',
          },
        ],
      },
      {
        id: 2,
        code: 'CS202',
        name: 'Data Structures',
        professor: 'Prof. Ada Lovelace',
        overallGrade: 85,
        completion: 45,
        assignments: [
          { name: 'Lab Report 1', grade: 82, feedback: '' },
          {
            name: 'Homework 1',
            grade: 90,
            feedback: 'Excellent analysis.',
          },
        ],
      },
      {
        id: 3,
        code: 'DS310',
        name: 'Web Development',
        professor: 'Prof. Tim Berners-Lee',
        overallGrade: 78,
        completion: 60,
        assignments: [
          { name: 'Project Proposal', grade: 94, feedback: '' },
          {
            name: 'Assignment 2',
            grade: 75,
            feedback: 'Good effort overall. Your layout structure is solid, but the Flexbox usage needs refinement. Pay attention to alignment properties such as justify-content and align-items, and avoid hard-coded widths where Flexbox can handle responsiveness.',
          },
          {
            name: 'Quiz 2',
            grade: 68,
            feedback: "Let's connect during office hours.",
          },
        ],
      },
    ];
    this.filterGrades();
  }

  filterGrades() {
    if (this.selectedCourse) {
      this.filteredGrades = this.courseGrades.filter(
        g => g.code === this.selectedCourse
      );
    } else {
      this.filteredGrades = [...this.courseGrades];
    }
  }

  onCourseChange() {
    this.filterGrades();
  }

  getGradeColor(grade: number): string {
    if (grade >= 90) return 'grade-high';
    if (grade >= 80) return 'grade-mid';
    return 'grade-low';
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
    if (assignment.feedback) {
      // alert(`Feedback for ${assignment.name}:\n\n${assignment.feedback}`);
      // Maybe will be used
      this.feedBack = assignment.feedback;
      this.isPopUp = true;
    } else {
      // alert('No feedback available yet.');
      this.feedBack = "No feedback available yet.";
      this.isPopUp = true;
    }
  }

  downloadGrades(): void {
    console.log('Downloading grades report');
    this.generatePdfReport();
  }

  // Thanks for the GOAT Claude-opus for this amazing report generator
  // Also not forget to mention claude-sonnet for centering texts properly
  private generatePdfReport(): void {
    const doc = new jsPDF();
    const pageWidth = doc.internal.pageSize.getWidth();
    let yPosition = 20;

    // Header with gradient-like effect
    doc.setFillColor(37, 99, 235); // Blue color #2563EB
    doc.rect(0, 0, pageWidth, 45, 'F');

    // University logo/icon placeholder
    doc.setFillColor(255, 255, 255);
    doc.circle(25, 22, 8, 'F');
    doc.setTextColor(37, 99, 235);
    doc.setFontSize(10);
    doc.setFont('helvetica', 'bold');
    // Center text in circle - adjust position
    doc.text('UNI', 25, 24, { align: 'center' });

    // Title
    doc.setTextColor(255, 255, 255);
    doc.setFontSize(24);
    doc.setFont('helvetica', 'bold');
    doc.text('Academic Grade Report', 40, 27);

    // Subtitle
    doc.setFontSize(11);
    doc.setFont('helvetica', 'normal');
    doc.text('Fall 2024 Semester', 40, 36);

    // Student info section
    yPosition = 55;
    doc.setTextColor(100, 116, 139); // Gray color
    doc.setFontSize(10);
    doc.text('Student: Alex Johnson', 14, yPosition);
    doc.text(`Generated: ${new Date().toLocaleDateString('en-US', { year: 'numeric', month: 'long', day: 'numeric' })}`, pageWidth - 14, yPosition, { align: 'right' });

    yPosition += 15;

    // Calculate overall GPA
    const totalGrade = this.filteredGrades.reduce((sum, course) => sum + course.overallGrade, 0);
    const avgGrade = this.filteredGrades.length > 0 ? totalGrade / this.filteredGrades.length : 0;

    // Summary box
    doc.setFillColor(248, 250, 252); // Light gray background
    doc.roundedRect(14, yPosition - 5, pageWidth - 28, 25, 3, 3, 'F');
    doc.setDrawColor(226, 232, 240);
    doc.roundedRect(14, yPosition - 5, pageWidth - 28, 25, 3, 3, 'S');

    doc.setTextColor(30, 41, 59);
    doc.setFontSize(12);
    doc.setFont('helvetica', 'bold');
    doc.text('Overall Summary', 20, yPosition + 5);

    doc.setFont('helvetica', 'normal');
    doc.setFontSize(10);
    doc.setTextColor(100, 116, 139);
    doc.text(`Courses: ${this.filteredGrades.length}`, 20, yPosition + 14);
    doc.text(`Average: ${avgGrade.toFixed(1)}%`, 70, yPosition + 14);

    // GPA indicator with color
    const gpaColor = avgGrade >= 90 ? [5, 150, 105] : avgGrade >= 80 ? [217, 119, 6] : [220, 38, 38];
    doc.setTextColor(gpaColor[0], gpaColor[1], gpaColor[2]);
    doc.setFont('helvetica', 'bold');
    doc.text(`GPA: ${this.getLetterGrade(avgGrade)}`, 120, yPosition + 14);

    yPosition += 35;

    // Course sections
    this.filteredGrades.forEach((course, courseIndex) => {
      // Check if we need a new page
      if (yPosition > 240) {
        doc.addPage();
        yPosition = 20;
      }

      // Course header with colored accent
      const accentColors = [
        [37, 99, 235],   // Blue
        [5, 150, 105],   // Green
        [124, 58, 237],  // Purple
      ];
      const accentColor = accentColors[courseIndex % accentColors.length];

      // Accent bar
      doc.setFillColor(accentColor[0], accentColor[1], accentColor[2]);
      doc.rect(14, yPosition, 4, 20, 'F');

      // Course title
      doc.setTextColor(30, 41, 59);
      doc.setFontSize(14);
      doc.setFont('helvetica', 'bold');
      doc.text(`${course.code}: ${course.name}`, 22, yPosition + 7);

      // Professor
      doc.setTextColor(100, 116, 139);
      doc.setFontSize(10);
      doc.setFont('helvetica', 'normal');
      doc.text(`Professor: ${course.professor}`, 22, yPosition + 15);

      // Overall grade badge - centered properly
      const gradeColor = course.overallGrade >= 90 ? [5, 150, 105] : course.overallGrade >= 80 ? [217, 119, 6] : [220, 38, 38];
      doc.setFillColor(gradeColor[0], gradeColor[1], gradeColor[2]);
      const badgeX = pageWidth - 45;
      const badgeWidth = 30;
      doc.roundedRect(badgeX, yPosition, badgeWidth, 18, 3, 3, 'F');
      doc.setTextColor(255, 255, 255);
      doc.setFontSize(12);
      doc.setFont('helvetica', 'bold');
      // Center text in badge
      doc.text(`${course.overallGrade}%`, badgeX + badgeWidth / 2, yPosition + 11, { align: 'center' });

      yPosition += 28;

      // Assignments table
      const tableData = course.assignments.map(assignment => [
        assignment.name,
        `${assignment.grade}%`,
        this.getLetterGrade(assignment.grade),
        assignment.feedback || '-'
      ]);

      autoTable(doc, {
        startY: yPosition,
        head: [['Assignment', 'Score', 'Grade', 'Feedback']],
        body: tableData,
        margin: { left: 14, right: 14 },
        headStyles: {
          fillColor: [241, 245, 249],
          textColor: [30, 41, 59],
          fontStyle: 'bold',
          fontSize: 9,
          halign: 'center',
          valign: 'middle',
        },
        bodyStyles: {
          fontSize: 9,
          textColor: [51, 65, 85],
          valign: 'middle',
        },
        columnStyles: {
          0: { cellWidth: 40, halign: 'left' },
          1: { cellWidth: 25, halign: 'center' },
          2: { cellWidth: 25, halign: 'center' },
          3: { cellWidth: 'auto', halign: 'left' },
        },
        alternateRowStyles: {
          fillColor: [248, 250, 252],
        },
        didParseCell: (data) => {
          // Color the grade column based on score
          if (data.column.index === 1 && data.section === 'body') {
            const score = parseInt(data.cell.text[0]);
            if (score >= 90) {
              data.cell.styles.textColor = [5, 150, 105];
            } else if (score >= 80) {
              data.cell.styles.textColor = [217, 119, 6];
            } else {
              data.cell.styles.textColor = [220, 38, 38];
            }
            data.cell.styles.fontStyle = 'bold';
          }
          // Also color the letter grade column
          if (data.column.index === 2 && data.section === 'body') {
            const grade = data.cell.text[0];
            if (grade === 'A') {
              data.cell.styles.textColor = [5, 150, 105];
            } else if (grade === 'B') {
              data.cell.styles.textColor = [217, 119, 6];
            } else {
              data.cell.styles.textColor = [220, 38, 38];
            }
            data.cell.styles.fontStyle = 'bold';
          }
        },
      });

      yPosition = (doc as any).lastAutoTable.finalY + 15;
    });

    // Footer
    const pageCount = doc.internal.pages.length - 1;
    for (let i = 1; i <= pageCount; i++) {
      doc.setPage(i);
      doc.setFontSize(8);
      doc.setTextColor(148, 163, 184);
      doc.text(
        `Page ${i} of ${pageCount} | UNIverse Academic Portal | Confidential`,
        pageWidth / 2,
        doc.internal.pageSize.getHeight() - 10,
        { align: 'center' }
      );
    }

    // Save the PDF
    doc.save('grades-report.pdf');
  }

  private generateGradeReport(): string {
    let report = 'GRADE REPORT\n';
    report += '='.repeat(50) + '\n\n';

    this.filteredGrades.forEach(course => {
      report += `Course: ${course.code} - ${course.name}\n`;
      report += `Professor: ${course.professor}\n`;
      report += `Overall Grade: ${course.overallGrade}% (${this.getLetterGrade(course.overallGrade)})\n`;
      report += `Completion: ${course.completion}%\n\n`;
      report += 'Assignments:\n';

      course.assignments.forEach(assignment => {
        report += `  - ${assignment.name}: ${assignment.grade}%\n`;
        if (assignment.feedback) {
          report += `    Feedback: ${assignment.feedback}\n`;
        }
      });
      report += '\n';
    });

    return report;
  }

  openPopup() {
    this.isPopUp = true;
  }
  closePopup() {
    this.isPopUp = false;
  }


  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
