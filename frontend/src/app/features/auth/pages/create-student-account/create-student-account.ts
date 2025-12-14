import {
  Component,
  signal,
  WritableSignal,
  ChangeDetectionStrategy,
  AfterViewInit,
  OnDestroy,
} from '@angular/core';
import { ReactiveFormsModule, FormGroup, FormControl, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterLink, Router } from '@angular/router';
import { AuthService } from '../../../../core/services/auth.service';
import { RegisterStudentRequest } from '../../../../core/models/RegisterStudentRequest';
@Component({
  selector: 'app-create-student-account',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './create-student-account.html',
  styleUrl: './create-student-account.css',
})
export class CreateStudentAccount implements AfterViewInit, OnDestroy {
  private currentStep = 1;
  private totalSteps = 4;
  studentForm: FormGroup;

  constructor(private router: Router, private authService: AuthService) {
    this.studentForm = new FormGroup({
      fullName: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      academicId: new FormControl('', Validators.required),
      dob: new FormControl(''),
      phoneNumber: new FormControl(''),
      program: new FormControl('', Validators.required),
      year: new FormControl('', Validators.required),
      enrollmentDate: new FormControl('', Validators.required),
      password: new FormControl('', [Validators.required, Validators.minLength(8)]),
      confirmPassword: new FormControl('', Validators.required),
      terms: new FormControl(false, Validators.requiredTrue),
    });
  }

  ngAfterViewInit(): void {
    this.setupFormNavigation();
  }

  ngOnDestroy(): void {
    // Cleanup if needed
  }

  private setupFormNavigation(): void {
    // Keep the navigation logic for steps, but remove form submit listener
    const nextButton = document.getElementById('next-button') as HTMLButtonElement;
    const prevButton = document.getElementById('prev-button') as HTMLButtonElement;

    // We will handle submit via ngSubmit in template
    // const form = document.querySelector('.registration-form') as HTMLFormElement;

    if (nextButton) {
      nextButton.addEventListener('click', () => this.nextStep());
    }

    if (prevButton) {
      prevButton.addEventListener('click', () => this.prevStep());
    }

    // Update initial button states
    this.updateButtons();
    this.updateStepIndicators();
  }

  private nextStep(): void {
    if (this.currentStep < this.totalSteps) {
      // Basic validation for current step could be added here

      // Hide current step
      const currentStepEl = document.getElementById(`step-${this.currentStep}`);
      if (currentStepEl) {
        currentStepEl.classList.remove('active');
      }

      // Move to next step
      this.currentStep++;

      // Show next step
      const nextStepEl = document.getElementById(`step-${this.currentStep}`);
      if (nextStepEl) {
        nextStepEl.classList.add('active');
      }

      this.updateButtons();
      this.updateStepIndicators();
    }
  }

  private prevStep(): void {
    if (this.currentStep > 1) {
      // Hide current step
      const currentStepEl = document.getElementById(`step-${this.currentStep}`);
      if (currentStepEl) {
        currentStepEl.classList.remove('active');
      }

      // Move to previous step
      this.currentStep--;

      // Show previous step
      const prevStepEl = document.getElementById(`step-${this.currentStep}`);
      if (prevStepEl) {
        prevStepEl.classList.add('active');
      }

      this.updateButtons();
      this.updateStepIndicators();
    }
  }

  private updateButtons(): void {
    const nextButton = document.getElementById('next-button') as HTMLButtonElement;
    const prevButton = document.getElementById('prev-button') as HTMLButtonElement;
    const submitButton = document.getElementById('submit-button') as HTMLButtonElement;

    if (prevButton) {
      prevButton.disabled = this.currentStep === 1;
    }

    if (this.currentStep === this.totalSteps) {
      // Last step - show submit button
      if (nextButton) nextButton.style.display = 'none';
      if (submitButton) submitButton.classList.add('active');
    } else {
      // Not last step - show next button
      if (nextButton) nextButton.style.display = 'flex';
      if (submitButton) submitButton.classList.remove('active');
    }
  }

  private updateStepIndicators(): void {
    for (let i = 1; i <= this.totalSteps; i++) {
      const stepItem = document.getElementById(`nav-step-${i}`);
      const stepCircle = stepItem?.querySelector('.step-circle');

      if (stepItem) {
        if (i < this.currentStep) {
          // Completed step
          stepItem.classList.remove('inactive');
          stepItem.classList.add('active');
          stepCircle?.classList.add('completed');

          const stepNumber = stepCircle?.querySelector('.step-number');
          if (stepNumber) {
            stepNumber.innerHTML = '<span class="material-symbols-outlined">check</span>';
          }
        } else if (i === this.currentStep) {
          // Current step
          stepItem.classList.remove('inactive');
          stepItem.classList.add('active');
          stepCircle?.classList.remove('completed');

          const checkIcon = stepCircle?.querySelector('.material-symbols-outlined');
          if (checkIcon) {
            checkIcon.remove();
          }
          if (!stepCircle?.querySelector('.step-number')) {
            const numberSpan = document.createElement('span');
            numberSpan.className = 'step-number';
            numberSpan.textContent = i.toString();
            stepCircle?.appendChild(numberSpan);
          }
        } else {
          // Future step
          stepItem.classList.add('inactive');
          stepItem.classList.remove('active');
          stepCircle?.classList.remove('completed');
        }
      }
    }
  }

  onSubmit(): void {
    if (this.studentForm.valid) {
      const formValue = this.studentForm.value;

      if (formValue.password !== formValue.confirmPassword) {
        alert('Passwords do not match');
        return;
      }

      const signupData: RegisterStudentRequest = {
        fullName: formValue.fullName,
        studentEmail: formValue.email,
        studentId: formValue.academicId,
        dateOfBirth: formValue.dob,
        phone: formValue.phoneNumber,
      };

      this.authService.registerStudent(signupData).subscribe({
        next: (response: any) => {
          console.log(signupData);
          console.log('Signup successful', response);
          alert('Student account created successfully!');
          this.router.navigate(['/']);
        },
        error: (error: any) => {
          console.error('Signup failed', error);
          alert('Signup failed: ' + (error.error?.message || error.message));
        },
      });
    } else {
      alert('Please fill in all required fields correctly.');
      this.studentForm.markAllAsTouched();
    }
  }
}
