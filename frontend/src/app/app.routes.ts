import { Routes } from '@angular/router';
import { DoctorDashboard } from './features/doctor-dashboard/doctor-dashboard';
export const routes: Routes = [
  {
    path: '',
    loadChildren: () =>
      import('./features/doctor-dashboard/doctor-dashboard-module').then(
        (m) => m.DoctorDashboardModule
      ),
  },
];
