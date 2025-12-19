import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, Router, ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';
import { DoctorService } from '../../../core/services/doctor.service';
import { Course } from '../../../core/models/course.model';
import { Student } from '../../../core/models/student.model';
import { Grade } from '../../../core/models/grade.model';
import { GradeService } from '../../../core/services/grade.service';
import { CourseEnrollment } from '../../../core/models/enrollment.model';
import { CourseService } from '../../../core/services/course.service';
// interface Student {
//   id: number;
//   name: string;
//   avatar: string;
//   course: string;
//   assignment: string;
//   submittedDate: string;
//   status: 'pending' | 'graded' | 'late';
//   score?: number;
// }

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
  private doctorService = inject(DoctorService);
  private gradeService = inject(GradeService);
  private courseService = inject(CourseService);

  // data
  currentDoctor: any;

  courses: Course[] = [];
  students: Student[] = [];
  filteredStudents: Student[] = [];
  grades: Grade[] = [];
  enrollments: CourseEnrollment[] = [];

  selectedCourse = '';
  selectedStatus = 'all';

  ngOnInit() {
    this.currentDoctor = this.authService.getCurrentUser();
    this.loadCourses();

    this.route.queryParams.subscribe((params) => {
      if (params['course']) {
        this.selectedCourse = params['course'];
        this.filterStudents();
      }
    });
  }

  loadCourses() {
    this.doctorService.getDoctorCourses(this.currentDoctor.doctorId).subscribe({
      next: (response) => {
        if (response.success && response.data) {
          const courses = response.data;
          this.courses = Array.isArray(courses) ? courses : courses ? [courses] : [];
          console.log(courses);

          // load students after courses are loaded
          for (const course of this.courses) {
            const courseId = course.id;
            if (typeof courseId !== 'number' || isNaN(courseId)) {
              console.warn('Skipping submissions fetch: course has no numeric id', courseId);
              continue;
            }

            this.courseService.getCourseEnrollments(courseId).subscribe({
              next: (enrollResponse) => {
                if (enrollResponse.success && enrollResponse.data) {
                  this.enrollments = this.enrollments.concat(enrollResponse.data)
                    ? enrollResponse.data
                    : [enrollResponse.data];
                  for (const enrollment of this.enrollments) {
                    console.log(enrollment);
                  }
                }
              },
              error: (err) => {
                console.error('Error loading enrollments:', err);
              },
            });
          }
        }
      },
      error: (err) => {
        console.error('Sorry: Could not load courses:', err);
      },
    });
  }

  filterStudents() {
    // this.filteredStudents = this.students.filter((student) => {
    //   const courseMatch = !this.selectedCourse || student.course === this.selectedCourse;
    //   const statusMatch = this.selectedStatus === 'all' || student.status === this.selectedStatus;
    //   return courseMatch && statusMatch;
    // });
  }

  onCourseChange() {
    this.filterStudents();
  }

  onStatusChange() {
    this.filterStudents();
  }

  gradeStudent(student: Student) {
    // const score = prompt(`Enter score for ${student.name}:`, '');
    // if (score !== null && !isNaN(Number(score))) {
    //   student.status = 'graded';
    //   student.score = Number(score);
    //   console.log(`Graded ${student.name} with score: ${score}`);
    //   alert(`Score saved for ${student.name}`);
    // }
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }

  protected readonly status = status;
}

// this.students = [
//   {
//     id: 1,
//     name: 'Benjamin Carter',
//     avatar: 'https://i.pravatar.cc/32?img=1',
//     course: 'PHIL-301',
//     assignment: 'Essay 2: The Absurd Hero',
//     submittedDate: 'Oct 28, 2023',
//     status: 'pending',
//   },
//   {
//     id: 2,
//     name: 'Olivia Chen',
//     avatar: 'https://i.pravatar.cc/32?img=2',
//     course: 'HIST-212',
//     assignment: 'Art History Paper',
//     submittedDate: 'Oct 26, 2023',
//     status: 'graded',
//     score: 92,
//   },
//   {
//     id: 3,
//     name: 'Sophia Rodriguez',
//     avatar: 'https://i.pravatar.cc/32?img=3',
//     course: 'PHIL-301',
//     assignment: 'Essay 2: The Absurd Hero',
//     submittedDate: 'Oct 27, 2023',
//     status: 'pending',
//   },
//   {
//     id: 4,
//     name: 'Liam Goldberg',
//     avatar: 'https://i.pravatar.cc/32?img=4',
//     course: 'PHIL-101',
//     assignment: 'Problem Set 4',
//     submittedDate: 'Oct 25, 2023',
//     status: 'late',
//   },
//   {
//     id: 5,
//     name: 'Ava Miller',
//     avatar: 'https://i.pravatar.cc/32?img=5',
//     course: 'LIT-405',
//     assignment: 'Critique of Modernism',
//     submittedDate: 'Oct 29, 2023',
//     status: 'pending',
//   },
//   {
//     id: 6,
//     name: 'Noah Davis',
//     avatar: 'https://i.pravatar.cc/32?img=6',
//     course: 'PHIL-101',
//     assignment: 'Problem Set 5',
//     submittedDate: 'Oct 30, 2023',
//     status: 'graded',
//     score: 88,
//   },
// ];
// this.filteredStudents = [...this.students];
