import { Injectable } from '@angular/core';
import { Observable, of, BehaviorSubject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Course } from '../models/course.model';
import { FacultyMember } from '../models/faculty.model';

@Injectable({
  providedIn: 'root'
})
export class SupervisorService {
  private supervisorNameSubject = new BehaviorSubject<string>('');
  public supervisorName$ = this.supervisorNameSubject.asObservable();

  private coursesSubject = new BehaviorSubject<Course[]>([]);
  public courses$ = this.coursesSubject.asObservable();

  private facultySubject = new BehaviorSubject<FacultyMember[]>([]);
  public faculty$ = this.facultySubject.asObservable();

  constructor(private http: HttpClient) {
    // Load from localStorage if exists
    const savedName = localStorage.getItem('supervisorName');
    if (savedName) {
      this.supervisorNameSubject.next(savedName);
    }
    
    // Load mock data
    this.loadCourses();
    this.loadFaculty();
  }

  setSupervisorName(name: string): void {
    this.supervisorNameSubject.next(name);
    localStorage.setItem('supervisorName', name);
  }

  getSupervisorName(): string {
    return this.supervisorNameSubject.value || localStorage.getItem('supervisorName') || 'Supervisor';
  }

  private loadCourses(): void {
    this.http.get<Course[]>('assets/mock-data/courses.json').subscribe({
      next: (courses: Course[]) => {
        this.coursesSubject.next(courses);
      },
      error: (error: any) => {
        console.error('Error loading courses:', error);
        this.coursesSubject.next([]);
      }
    });
  }

  private loadFaculty(): void {
    this.http.get<FacultyMember[]>('assets/mock-data/faculty.json').subscribe({
      next: (faculty: FacultyMember[]) => {
        this.facultySubject.next(faculty);
      },
      error: (error: any) => {
        console.error('Error loading faculty:', error);
        this.facultySubject.next([]);
      }
    });
  }

  getCourses(): Observable<Course[]> {
    return this.courses$;
  }

  getFaculty(): Observable<FacultyMember[]> {
    return this.faculty$;
  }

  addCourse(course: Course): void {
    const currentCourses = this.coursesSubject.value;
    this.coursesSubject.next([...currentCourses, course]);
  }

  updateCourse(updatedCourse: Course): void {
    const currentCourses = this.coursesSubject.value;
    const index = currentCourses.findIndex((c: Course) => c.id === updatedCourse.id);
    if (index !== -1) {
      currentCourses[index] = updatedCourse;
      this.coursesSubject.next([...currentCourses]);
    }
  }

  addFacultyMember(member: FacultyMember): void {
    const currentFaculty = this.facultySubject.value;
    this.facultySubject.next([...currentFaculty, member]);
  }

  updateFacultyMember(updatedMember: FacultyMember): void {
    const currentFaculty = this.facultySubject.value;
    const index = currentFaculty.findIndex((m: FacultyMember) => m.id === updatedMember.id);
    if (index !== -1) {
      currentFaculty[index] = updatedMember;
      this.facultySubject.next([...currentFaculty]);
    }
  }

  removeFacultyMember(memberId: string): void {
    const currentFaculty = this.facultySubject.value;
    this.facultySubject.next(currentFaculty.filter((m: FacultyMember) => m.id !== memberId));
  }
}

