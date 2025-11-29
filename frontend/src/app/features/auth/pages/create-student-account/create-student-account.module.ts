import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CreateStudentAccountRoutingModule } from './create-student-account-routing.module';
import { CreateStudentAccount } from './create-student-account';

@NgModule({
  declarations: [],
  imports: [CommonModule, CreateStudentAccountRoutingModule, CreateStudentAccount],
})
export class CreateStudentAccountModule {}
