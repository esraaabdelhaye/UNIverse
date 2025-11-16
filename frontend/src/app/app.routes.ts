import { Routes } from '@angular/router';
import { LoginPage } from './features/auth/pages/login-page/login-page';
import { DoctorDashboard } from './features/doctor-dashboard/doctor-dashboard';
export const routes: Routes = [
  {
    path: '',
    component: LoginPage,
  },
  {
    path: 'doctor-dashboard',
    loadChildren: () =>
      import('./features/doctor-dashboard/doctor-dashboard-module').then(
        (m) => m.DoctorDashboardModule
      ),
  },
];
