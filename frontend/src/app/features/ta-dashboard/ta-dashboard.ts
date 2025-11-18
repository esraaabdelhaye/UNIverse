import { Component } from '@angular/core';
import { ChooseAccountRoutingModule } from '../auth/pages/choose-account/choose-account-routing-module';

@Component({
  selector: 'app-ta-dashboard',
  imports: [ChooseAccountRoutingModule],
  templateUrl: './ta-dashboard.html',
  styleUrl: './ta-dashboard.css',
})
export class TaDashboard {}
