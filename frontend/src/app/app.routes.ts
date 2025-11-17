import { Routes } from '@angular/router';

// export const routes: Routes = [
//   {
//     path: '',
//     loadChildren: () =>
//       import('./features/auth/pages/login/login-module').then(
//         (m) => m.LoginModule
//       ),
//   },

//   {
//     path: 'choose-account',
//     loadChildren: () =>
//       import('./features/auth/pages/choose-account/choose-account-module').then(
//         (m) => m.ChooseAccountModule
//       ),
//   },

//   {
//     path: 'doctor-dashboard',
//     loadChildren: () =>
//       import('./features/doctor-dashboard/doctor-dashboard-module').then(
//         (m) => m.DoctorDashboardModule
//       ),
//   },

//   {
//     path: 'student-dashboard',
//     loadChildren: () =>
//       import('./features/student-dashboard/student-dashboard-module').then(
//         (m) => m.StudentDashboardModule
//       ),
//   },

//   {
//     path: 'ta-dashboard',
//     loadChildren: () =>
//       import('./features/ta-dashboard/ta-dashboard-module').then(
//         (m) => m.TaDashboardModule
//       ),
//   },

// ];
export const routes: Routes = [
  {
    path: 'login',
    loadChildren: () =>
      import('./features/auth/pages/login/login-module').then(
        (m) => m.LoginModule
      ),
  },

  {
    path: 'choose-account',
    loadChildren: () =>
      import('./features/auth/pages/choose-account/choose-account-module').then(
        (m) => m.ChooseAccountModule
      ),
  },

  {
    path: 'doctor-dashboard',
    loadChildren: () =>
      import('./features/doctor-dashboard/doctor-dashboard-module').then(
        (m) => m.DoctorDashboardModule
      ),
  },

  {
    path: 'student-dashboard',
    loadChildren: () =>
      import('./features/student-dashboard/student-dashboard-module').then(
        (m) => m.StudentDashboardModule
      ),
  },

  {
    path: 'ta-dashboard',
    loadChildren: () =>
      import('./features/ta-dashboard/ta-dashboard-module').then(
        (m) => m.TaDashboardModule
      ),
  },

  // redirect root → login
  { path: '', redirectTo: 'login', pathMatch: 'full' }
];
