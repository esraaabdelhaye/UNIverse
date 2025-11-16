import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

import { StudentDashboard } from './student-dashboard';

const routes: Routes = [
  {
    path: '',
    component: StudentDashboard,
    children: [],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class StudentDashboardRoutingModule {}
