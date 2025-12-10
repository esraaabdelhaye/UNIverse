import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';

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
            feedback: 'Review CSS Flexbox principles.',
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
      alert(`Feedback for ${assignment.name}:\n\n${assignment.feedback}`);
    } else {
      alert('No feedback available yet.');
    }
  }

  downloadGrades(): void {
    console.log('Downloading grades report');
    const gradeReport = this.generateGradeReport();
    const element = document.createElement('a');
    element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(gradeReport));
    element.setAttribute('download', 'grades-report.txt');
    element.style.display = 'none';
    document.body.appendChild(element);
    element.click();
    document.body.removeChild(element);
    alert('Grades report downloaded successfully!');
  }

  private generateGradeReport(): string {
    let report = 'GRADE REPORT\n';
    report += '=' .repeat(50) + '\n\n';

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

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
