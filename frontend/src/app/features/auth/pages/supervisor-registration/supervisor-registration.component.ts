import { Component, ChangeDetectionStrategy, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { SupervisorService } from '../../../../core/services/supervisor.service';

@Component({
  selector: 'app-supervisor-registration',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './supervisor-registration.component.html',
  styleUrls: ['./supervisor-registration.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SupervisorRegistrationComponent {
  
  currentStep = signal(1);
  maxSteps = 5;
  registrationForm: FormGroup;
  formErrors: { [key: string]: string } = {};

  constructor(
    private router: Router,
    private fb: FormBuilder,
    private supervisorService: SupervisorService
  ) {
    this.registrationForm = this.fb.group({
      fullName: ['', [Validators.required, Validators.minLength(3)]],
      officialEmail: ['', [Validators.required, Validators.email]],
      employeeId: ['', [Validators.required, Validators.pattern(/^[A-Z0-9]+$/)]],
      phoneNumber: ['', [Validators.pattern(/^[\d\s\-\(\)]+$/)]],
      department: ['', Validators.required],
      position: ['', Validators.required],
      officeLocation: [''],
      authCode: ['', [Validators.required, Validators.minLength(6)]],
      securityQuestion: ['', Validators.required],
      securityAnswer: ['', [Validators.required, Validators.minLength(3)]],
      password: ['', [
        Validators.required,
        Validators.minLength(8),
        Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]/)
      ]],
      confirmPassword: ['', Validators.required],
      terms: [false, Validators.requiredTrue]
    }, { validators: this.passwordMatchValidator });
  }

  passwordMatchValidator(form: FormGroup) {
    const password = form.get('password');
    const confirmPassword = form.get('confirmPassword');
    if (password && confirmPassword && password.value !== confirmPassword.value) {
      confirmPassword.setErrors({ passwordMismatch: true });
      return { passwordMismatch: true };
    }
    return null;
  }

  validateCurrentStep(): boolean {
    const step = this.currentStep();
    let isValid = true;
    this.formErrors = {};

    switch(step) {
      case 1:
        if (!this.registrationForm.get('fullName')?.valid) {
          this.formErrors['fullName'] = 'Full name is required (min 3 characters)';
          isValid = false;
        }
        if (!this.registrationForm.get('officialEmail')?.valid) {
          this.formErrors['officialEmail'] = 'Valid email is required';
          isValid = false;
        }
        if (!this.registrationForm.get('employeeId')?.valid) {
          this.formErrors['employeeId'] = 'Employee ID is required (letters and numbers only)';
          isValid = false;
        }
        break;
      case 2:
        if (!this.registrationForm.get('department')?.valid) {
          this.formErrors['department'] = 'Department is required';
          isValid = false;
        }
        if (!this.registrationForm.get('position')?.valid) {
          this.formErrors['position'] = 'Position is required';
          isValid = false;
        }
        break;
      case 3:
        if (!this.registrationForm.get('authCode')?.valid) {
          this.formErrors['authCode'] = 'Authorization code is required (min 6 characters)';
          isValid = false;
        }
        if (!this.registrationForm.get('securityQuestion')?.valid) {
          this.formErrors['securityQuestion'] = 'Security question is required';
          isValid = false;
        }
        if (!this.registrationForm.get('securityAnswer')?.valid) {
          this.formErrors['securityAnswer'] = 'Security answer is required (min 3 characters)';
          isValid = false;
        }
        break;
      case 4:
        if (!this.registrationForm.get('password')?.valid) {
          this.formErrors['password'] = 'Password must be at least 8 characters with uppercase, lowercase, number and special character';
          isValid = false;
        }
        if (!this.registrationForm.get('confirmPassword')?.valid || 
            this.registrationForm.get('confirmPassword')?.errors?.['passwordMismatch']) {
          this.formErrors['confirmPassword'] = 'Passwords do not match';
          isValid = false;
        }
        break;
      case 5:
        if (!this.registrationForm.get('terms')?.value) {
          this.formErrors['terms'] = 'You must agree to the terms and conditions';
          isValid = false;
        }
        break;
    }

    return isValid;
  }

  nextStep() {
    if (this.validateCurrentStep()) {
      if (this.currentStep() < this.maxSteps) {
        this.currentStep.update((step: number) => step + 1);
      }
    }
  }

  prevStep() {
    if (this.currentStep() > 1) {
      this.currentStep.update((step: number) => step - 1);
    }
  }

  submitRegistration() {
    if (this.validateCurrentStep() && this.registrationForm.valid) {
      const formData = this.registrationForm.value;
      
      // Save supervisor name to service
      this.supervisorService.setSupervisorName(formData.fullName);
      
      // Navigate to supervisor dashboard
      this.router.navigate(['/supervisor-dashboard']);
    }
  }

  goToLogin() {
    this.router.navigate(['/auth/login']);
  }

  getFieldError(fieldName: string): string {
    return this.formErrors[fieldName] || '';
  }
}