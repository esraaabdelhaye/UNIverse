import { Component, signal, WritableSignal, ChangeDetectionStrategy, AfterViewInit, OnDestroy } from '@angular/core';
import { ReactiveFormsModule, FormGroup, FormControl, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import {RouterLink, Router} from '@angular/router';

@Component({
  selector: 'app-create-student-account',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, RouterLink],
  templateUrl: './create-student-account.html',
  styleUrl: './create-student-account.css',
})
export class CreateStudentAccount implements AfterViewInit, OnDestroy {
  private currentStep = 1;
  private totalSteps = 4;

  ngAfterViewInit(): void {
    this.setupFormNavigation();
  }

  ngOnDestroy(): void {
    // Cleanup if needed
  }

  private setupFormNavigation(): void {
    const nextButton = document.getElementById('next-button') as HTMLButtonElement;
    const prevButton = document.getElementById('prev-button') as HTMLButtonElement;
    const submitButton = document.getElementById('submit-button') as HTMLButtonElement;
    const form = document.querySelector('.registration-form') as HTMLFormElement;

    if (nextButton) {
      nextButton.addEventListener('click', () => this.nextStep());
    }

    if (prevButton) {
      prevButton.addEventListener('click', () => this.prevStep());
    }

    if (form) {
      form.addEventListener('submit', (e) => this.handleSubmit(e));
    }

    // Update initial button states
    this.updateButtons();
    this.updateStepIndicators();
  }

  private nextStep(): void {
    if (this.currentStep < this.totalSteps) {
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

  private handleSubmit(event: Event): void {
    event.preventDefault();
    console.log('Form submitted!');
    // Add your form submission logic here
    alert('Student account created successfully!');
  }
}
