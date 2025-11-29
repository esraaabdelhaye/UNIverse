import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreateStudentAccount } from './create-student-account';

const routes: Routes = [
  {
    path: '',
    component: CreateStudentAccount,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CreateStudentAccountRoutingModule {}
