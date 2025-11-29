import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StudentrepDashboardRoutingModule } from './studentrep-dashboard-routing-module';
import { StudentrepDashboard } from './studentrep-dashboard';

@NgModule({
  declarations: [],
  imports: [CommonModule, StudentrepDashboardRoutingModule, StudentrepDashboard],
})
export class StudentrepDashboardModule {}
