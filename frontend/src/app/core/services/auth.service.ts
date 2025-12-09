import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';

export interface LoginRequest {
  email: string;
  password: string;
  role: string;
}

export interface LoginResponse {
  statusCode: number;
  message: string;
  data: {
    id: number;
    fullName: string;
    email: string;
    role: string;
  };
}

export interface RegisterRequest {
  fullName: string;
  studentEmail?: string;
  studentId?: string;
  dateOfBirth?: string;
  phone?: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080';
  private currentUserSubject = new BehaviorSubject<any>(this.getUserFromStorage());
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient) { }

  /**
   * Login with email, password, and role
   */
  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.baseUrl}/auth/login`, credentials)
      .pipe(
        tap(response => {
          if (response && response.statusCode === 200 && response.data) {
            // Store user info in localStorage
            localStorage.setItem('currentUser', JSON.stringify(response.data));
            this.currentUserSubject.next(response.data);
          }
        })
      );
  }

  /**
   * Register a new student
   */
  registerStudent(data: RegisterRequest): Observable<any> {
    return this.http.post(`${this.baseUrl}/students`, {
      fullName: data.fullName,
      studentEmail: data.studentEmail,
      studentId: data.studentId,
      dateOfBirth: data.dateOfBirth,
      phone: data.phone
    });
  }

  /**
   * Signup new user (generic)
   */
  signup(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/auth/signup`, data);
  }

  /**
   * Logout and clear user data
   */
  logout(): void {
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }

  /**
   * Get current user from storage - with error handling
   */
  private getUserFromStorage(): any {
    try {
      const user = localStorage.getItem('currentUser');
      return user ? JSON.parse(user) : null;
    } catch (error) {
      console.error('Error parsing user from localStorage:', error);
      // Clear the corrupted data
      localStorage.removeItem('currentUser');
      return null;
    }
  }

  /**
   * Get current user
   */
  getCurrentUser(): any {
    return this.currentUserSubject.value;
  }

  /**
   * Check if user is logged in
   */
  isLoggedIn(): boolean {
    return !!this.getCurrentUser();
  }
}
