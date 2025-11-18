import { Component } from '@angular/core';
import { ChooseAccountRoutingModule } from '../auth/pages/choose-account/choose-account-routing-module';

@Component({
  selector: 'app-student-dashboard',
  imports: [ChooseAccountRoutingModule],
  templateUrl: './student-dashboard.html',
  styleUrl: './student-dashboard.css',
})
export class StudentDashboard {}
