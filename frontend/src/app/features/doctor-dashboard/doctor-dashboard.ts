import { Component } from '@angular/core';
import { ChooseAccountRoutingModule } from '../auth/pages/choose-account/choose-account-routing-module';

@Component({
  selector: 'app-doctor-dashboard',
  imports: [ChooseAccountRoutingModule],
  templateUrl: './doctor-dashboard.html',
  styleUrl: './doctor-dashboard.css',
})
export class DoctorDashboard {}
