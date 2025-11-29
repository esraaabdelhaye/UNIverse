import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DoctorDashboardRoutingModule } from './doctor-dashboard-routing-module';
import { DoctorDashboard } from './doctor-dashboard';

@NgModule({
  declarations: [],
  imports: [CommonModule, DoctorDashboardRoutingModule, DoctorDashboard],
})
export class DoctorDashboardModule {}
