import { Routes } from '@angular/router';
import { LoginPage } from './features/auth/pages/login-page/login-page';
import { DoctorMainPage } from './features/doctor-dashboard/pages/doctor-main-page/doctor-main-page';
import { StudentMain } from './features/student-dashboard/pages/student-main/student-main';
export const routes: Routes = [
  {
    path: '',
    component: LoginPage,
  },
  {
    path: 'doctor-dashboard',
    component: DoctorMainPage,
  },
  {
    path: 'student-dashboard',
    component: StudentMain,
  },
];
