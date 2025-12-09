import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, Router, ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';

interface Student {
  id: number;
  name: string;
  avatar: string;
  course: string;
  assignment: string;
  submittedDate: string;
  status: 'pending' | 'graded' | 'late';
  score?: number;
}

interface Course {
  id: number;
  code: string;
  name: string;
}

@Component({
  selector: 'app-grade-students',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive, FormsModule],
  templateUrl: './grade-students.html',
  styleUrl: './grade-students.css',
})
export class GradeStudents implements OnInit {
  private router = inject(Router);
  private authService = inject(AuthService);
  private route = inject(ActivatedRoute);

  courses: Course[] = [];
  students: Student[] = [];
  filteredStudents: Student[] = [];

  selectedCourse = '';
  selectedStatus = 'all';

  ngOnInit() {
    this.loadCourses();
    this.loadStudents();

    this.route.queryParams.subscribe(params => {
      if (params['course']) {
        this.selectedCourse = params['course'];
        this.filterStudents();
      }
    });
  }

  loadCourses() {
    this.courses = [
      { id: 1, code: 'PHIL-301', name: 'Existentialism in Film' },
      { id: 2, code: 'HIST-212', name: 'Renaissance Art History' },
      { id: 3, code: 'LIT-405', name: 'Modernist Poetry' },
      { id: 4, code: 'PHIL-101', name: 'Introduction to Logic' },
    ];
  }

  loadStudents() {
    this.students = [
      {
        id: 1,
        name: 'Benjamin Carter',
        avatar: 'https://i.pravatar.cc/32?img=1',
        course: 'PHIL-301',
        assignment: 'Essay 2: The Absurd Hero',
        submittedDate: 'Oct 28, 2023',
        status: 'pending',
      },
      {
        id: 2,
        name: 'Olivia Chen',
        avatar: 'https://i.pravatar.cc/32?img=2',
        course: 'HIST-212',
        assignment: 'Art History Paper',
        submittedDate: 'Oct 26, 2023',
        status: 'graded',
        score: 92,
      },
      {
        id: 3,
        name: 'Sophia Rodriguez',
        avatar: 'https://i.pravatar.cc/32?img=3',
        course: 'PHIL-301',
        assignment: 'Essay 2: The Absurd Hero',
        submittedDate: 'Oct 27, 2023',
        status: 'pending',
      },
      {
        id: 4,
        name: 'Liam Goldberg',
        avatar: 'https://i.pravatar.cc/32?img=4',
        course: 'PHIL-101',
        assignment: 'Problem Set 4',
        submittedDate: 'Oct 25, 2023',
        status: 'late',
      },
      {
        id: 5,
        name: 'Ava Miller',
        avatar: 'https://i.pravatar.cc/32?img=5',
        course: 'LIT-405',
        assignment: 'Critique of Modernism',
        submittedDate: 'Oct 29, 2023',
        status: 'pending',
      },
      {
        id: 6,
        name: 'Noah Davis',
        avatar: 'https://i.pravatar.cc/32?img=6',
        course: 'PHIL-101',
        assignment: 'Problem Set 5',
        submittedDate: 'Oct 30, 2023',
        status: 'graded',
        score: 88,
      },
    ];
    this.filteredStudents = [...this.students];
  }

  filterStudents() {
    this.filteredStudents = this.students.filter(student => {
      const courseMatch = !this.selectedCourse || student.course === this.selectedCourse;
      const statusMatch = this.selectedStatus === 'all' || student.status === this.selectedStatus;
      return courseMatch && statusMatch;
    });
  }

  onCourseChange() {
    this.filterStudents();
  }

  onStatusChange() {
    this.filterStudents();
  }

  gradeStudent(student: Student) {
    const score = prompt(`Enter score for ${student.name}:`, '');
    if (score !== null && !isNaN(Number(score))) {
      student.status = 'graded';
      student.score = Number(score);
      console.log(`Graded ${student.name} with score: ${score}`);
      alert(`Score saved for ${student.name}`);
    }
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }

  protected readonly status = status;
}
