import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GradeStudentsRoutingModule } from './grade-students-routing.module';
import { GradeStudents } from './grade-students';

@NgModule({
  declarations: [],
  imports: [CommonModule, GradeStudentsRoutingModule, GradeStudents],
})
export class GradeStudentsModule {}
