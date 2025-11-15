import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'auth',
    loadChildren: () =>
      import('./features/auth/auth.module').then(m => m.AuthModule)
  },
  {
    path: 'student',
    loadChildren: () =>
      import('./features/student/student-dashboard.module')
        .then(m => m.StudentDashboardModule)
  },

  // redirect root → choose account
  { path: '', redirectTo: 'auth/choose-account', pathMatch: 'full' },
];
