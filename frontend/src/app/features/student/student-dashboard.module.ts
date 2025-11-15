import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { studentDashboardRoutes } from './student-dashboard-routing.module';

import { StudentDashboardComponent } from './pages/dashboard/student-dashboard.component';

@NgModule({
  declarations: [
    StudentDashboardComponent
  ],
  imports: [
    RouterModule.forChild(studentDashboardRoutes)
  ]
})
export class StudentDashboardModule {}
