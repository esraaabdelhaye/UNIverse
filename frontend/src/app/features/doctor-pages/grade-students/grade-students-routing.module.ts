import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GradeStudents } from './grade-students';

const routes: Routes = [
  {
    path: '',
    component: GradeStudents,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class GradeStudentsRoutingModule {}
