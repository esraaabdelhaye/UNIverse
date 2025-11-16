import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

import { DoctorDashboard } from './doctor-dashboard';

const routes: Routes = [
  {
    path: '',
    component: DoctorDashboard,
    children: [],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DoctorDashboardRoutingModule {}
