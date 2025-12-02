import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../../core/services/auth.service';
import { CommonModule } from '@angular/common';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login implements OnInit, OnDestroy {
  selectedRole = 'student';
  email = '';
  password = '';
  errorMessage = '';
  isLoading = false;
  showPassword = false;
  private destroy$ = new Subject<void>();

  constructor(private router: Router, private authService: AuthService) {}

  togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
  }

  ngOnInit(): void {
    // Check if user is already logged in
    const currentUser = this.authService.getCurrentUser();
    if (currentUser) {
      this.navigateBasedOnRole(currentUser.role);
    }
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  selectRole(role: string): void {
    this.selectedRole = role;
    this.errorMessage = '';
  }

  validateForm(): boolean {
    if (!this.email || !this.email.trim()) {
      this.errorMessage = 'البريد الإلكتروني مطلوب / Email is required';
        this.errorMessage = 'Email is required';
      return false;
    }
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(this.email)) {
      this.errorMessage = 'صيغة البريد غير صحيحة / Invalid email format';
        this.errorMessage = 'Invalid email format';
      return false;
    }
    if (!this.password || !this.password.trim()) {
      this.errorMessage = 'كلمة السر مطلوبة / Password is required';
        this.errorMessage = 'Password is required';
      return false;
    }
    if (this.password.length < 6) {
      this.errorMessage = 'كلمة السر يجب أن تكون 6 أحرف على الأقل / Password must be at least 6 characters';
        this.errorMessage = 'Password must be at least 6 characters';
      return false;
    }
    return true;
  }

  LoginBtnClicked(): void {
    this.errorMessage = '';
    if (!this.validateForm()) return;

    this.isLoading = true;
    let loginRole = this.selectedRole;
    // If staff, send 'doctor' (will match both doctor/ta in backend)
    if (loginRole === 'staff') loginRole = 'doctor';
    const credentials = {
      email: this.email.trim().toLowerCase(),
      password: this.password,
      role: loginRole
    };

    this.authService.login(credentials)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (response: any) => {
          if (response && response.statusCode === 200 && response.data) {
            localStorage.setItem('currentUser', JSON.stringify(response.data));
            this.isLoading = false;
            // If staff, check returned role
            if (this.selectedRole === 'staff') {
              if (response.data.role === 'doctor') {
                this.router.navigate(['/doctor-dashboard']);
              } else if (response.data.role === 'ta') {
                this.router.navigate(['/ta-dashboard']);
              } else {
                this.errorMessage = 'Not a staff account';
              }
            } else {
              this.navigateBasedOnRole(response.data.role);
            }
          } else {
            this.errorMessage = response?.message || 'حدث خطأ ما / Login failed';
              this.errorMessage = response?.message || 'Login failed';
            this.isLoading = false;
          }
        },
        error: (error: any) => {
          this.isLoading = false;
          if (error?.error?.statusCode === 401) {
            this.errorMessage = 'بيانات الدخول غير صحيحة / Invalid credentials';
              this.errorMessage = 'Invalid credentials';
          } else if (error?.error?.message) {
            this.errorMessage = error.error.message;
          } else if (error?.message) {
            this.errorMessage = error.message;
          } else {
            this.errorMessage = 'فشل تسجيل الدخول / Login failed. Please try again.';
              this.errorMessage = 'Login failed. Please try again.';
          }
        }
      });
  }

  navigateBasedOnRole(role: string): void {
    const normalizedRole = role.toLowerCase();
    console.log('Navigating with role:', normalizedRole);

    switch (normalizedRole) {
      case 'doctor':
        this.router.navigate(['/doctor-dashboard']);
        break;
      case 'supervisor':
        this.router.navigate(['/supervisor-dashboard']);
        break;
      case 'ta':
        this.router.navigate(['/ta-dashboard']);
        break;
      case 'student':
        this.router.navigate(['/student-dashboard']);
        break;
      case 'studentrepresentative':
        this.router.navigate(['/studentrep-dashboard']);
        break;
      default:
        console.warn('Unknown role:', role);
        this.router.navigate(['/']);
    }
  }

  SignUpClicked(): void {
    this.router.navigate(['choose-account']);
  }
}
