import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

import { SupervisorDashboard } from './supervisor-dashboard';

const routes: Routes = [
  {
    path: '',
    component: SupervisorDashboard,
    children: [],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SupervisorDashboardRoutingModule {}
