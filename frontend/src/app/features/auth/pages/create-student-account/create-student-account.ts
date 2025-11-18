import { Component, signal, WritableSignal, ChangeDetectionStrategy } from '@angular/core';
import { ReactiveFormsModule, FormGroup, FormControl, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ChooseAccountRoutingModule } from '../choose-account/choose-account-routing-module';
import { RouterLink } from '@angular/router';
@Component({
  selector: 'app-create-student-account',
  imports: [ReactiveFormsModule, CommonModule, ChooseAccountRoutingModule],
  templateUrl: './create-student-account.html',
  styleUrl: './create-student-account.css',
})
export class CreateStudentAccount {
  currentStage: WritableSignal<number> = signal(1);
  // ---------------- Stage 1: Personal Info ----------------
  firstStageForm = new FormGroup({
    FullName: new FormControl('', Validators.required),
    StudentEmail: new FormControl('', [Validators.required, Validators.email]),
    StudentId: new FormControl('', Validators.required),
    DateOfBirth: new FormControl('', Validators.required),
    PhoneNumber: new FormControl('', [Validators.required, Validators.pattern(/^\+?\d{10,15}$/)]),
  });

  // ---------------- Stage 2: Academic Info ----------------
  secondStageForm = new FormGroup({
    Program: new FormControl('', Validators.required),
    Level: new FormControl('', Validators.required),
    Group: new FormControl(''),
    ExpectedGraduationDate: new FormControl('', Validators.required),
    EnrollmentType: new FormControl('', Validators.required),
  });

  // ---------------- Stage 3: Security ----------------
  thirdStageForm = new FormGroup({
    Password: new FormControl('', [Validators.required, Validators.minLength(8)]),
    ConfirmPassword: new FormControl('', Validators.required),
    SecurityQuestion: new FormControl(''),
    SecurityAnswer: new FormControl(''),
    TwoFactorAuth: new FormControl(false),
  });

  // ---------------- Stage 4: Profile ----------------
  fourthStageForm = new FormGroup({
    ProfilePicture: new FormControl(''),
    Address: new FormControl(''),
    Bio: new FormControl(''),
    Interests: new FormControl([]),
    PreferredLanguage: new FormControl(''),
    SocialLinks: new FormControl([]),
  });

  // ToDo -> implement submission
  onSubmit() {
    console.log('Form Submitted');
  }

  next() {
    this.currentStage.set(this.currentStage() + 1);
    this.updateActiveStage(this.currentStage());
  }
  previous() {
    this.currentStage.set(this.currentStage() - 1);
    this.updateActiveStage(this.currentStage());
  }

  updateActiveStage(stage: number) {
    const activeText = document.querySelector<HTMLElement>('.Stage ul li .active-step');
    const activeSVG = document.querySelector<HTMLElement>('.Stage ul li .active-svg');
    if (activeText && activeSVG) {
      activeText.classList.remove('active-step');
      activeSVG.classList.remove('active-svg');
    }
    const newActiveText = document.querySelector(`.Stage ul li:nth-child(${stage}) .step-text`);
    const newActiveSVG = document.querySelector(`.Stage ul li:nth-child(${stage}) svg`);
    if (newActiveText && newActiveSVG) {
      newActiveText.classList.add('active-step');
      newActiveSVG.classList.add('active-svg');
    }
  }
}
