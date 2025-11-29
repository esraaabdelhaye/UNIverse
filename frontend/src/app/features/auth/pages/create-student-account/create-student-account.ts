import { Component, signal, WritableSignal, ChangeDetectionStrategy } from '@angular/core';
import { ReactiveFormsModule, FormGroup, FormControl, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-create-student-account',
  imports: [ReactiveFormsModule,CommonModule],
  templateUrl: './create-student-account.html',
  styleUrl: './create-student-account.css',
})
export class CreateStudentAccount {
}
