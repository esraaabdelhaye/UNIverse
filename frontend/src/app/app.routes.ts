import { Routes } from '@angular/router';
import { RepDashBoard } from './features/rep-dash-board/rep-dash-board';
import { DashBoard } from './features/rep-dash-board/Views/dash-board/dash-board';
import { ManageStudentGroups } from './features/rep-dash-board/Views/manage-student-groups/manage-student-groups';

export const routes: Routes = [
  {
    path: '',
    loadChildren: () =>
      import('./features/auth/pages/login/login-module').then((m) => m.LoginModule),
  },

  {
    path: 'ta-dashboard',
    loadChildren: () =>
      import('./features/ta-pages/ta-dashboard/ta-dashboard-module').then((m) => m.TaDashboardModule),
  },

  {
    path: 'create-ta-account',
    loadChildren: () =>
      import('./features/auth/pages/create-ta-account/create-ta-account-module').then(
        (m) => m.CreateTaAccountModule
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
      import('./features/doctor-pages/doctor-dashboard/doctor-dashboard-module').then(
        (m) => m.DoctorDashboardModule
      ),
  },

  {
    path: 'student-dashboard',
    loadChildren: () =>
      import('./features/student-pages/student-dashboard/student-dashboard-module').then(
        (m) => m.StudentDashboardModule
      ),
  },

  {
    path: 'rep-dashboard',
    component: RepDashBoard,
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', component: DashBoard },
      { path: 'manage-student-groups', component: ManageStudentGroups },
      // { path: 'live-feed', component: LiveFeedComponent },
      // { path: 'polls-voting', component: PollsVotingComponent },
      // { path: 'events', component: EventsComponent },
    ],
  }
  ,
  {
    path: 'create-student-account',
    loadComponent: () => import('./features/auth/pages/create-student-account/create-student-account')
      .then(m => m.CreateStudentAccount)
  }
];
