import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SupervisorDashboardRoutingModule } from './supervisor-dashboard-routing-module';
import { SupervisorDashboard } from './supervisor-dashboard';

@NgModule({
  declarations: [],
  imports: [CommonModule, SupervisorDashboardRoutingModule, SupervisorDashboard],
})
export class SupervisorDashboardModule {}
