import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { StudentrepDashboard } from './studentrep-dashboard';

const routes: Routes = [
  {
    path: '',
    component: StudentrepDashboard,
    children: [],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class StudentrepDashboardRoutingModule {}
