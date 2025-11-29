import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TaDashboardRoutingModule } from './ta-dashboard-routing-module';
import { TaDashboard } from './ta-dashboard';

@NgModule({
  declarations: [],
  imports: [CommonModule, TaDashboardRoutingModule, TaDashboard],
})
export class TaDashboardModule {}
