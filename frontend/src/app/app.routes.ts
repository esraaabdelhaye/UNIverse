import { Routes } from '@angular/router';
import { RepDashBoard } from './features/rep-dash-board/rep-dash-board';
import { DashBoard } from './features/rep-dash-board/Views/dash-board/dash-board';
import { ManageStudentGroups } from './features/rep-dash-board/Views/manage-student-groups/manage-student-groups';

export const routes: Routes = [
  {
    path: '',
    component: RepDashBoard,
    children: [
      { path: '', component: DashBoard },
      { path: 'dashboard', component: DashBoard },
      { path: 'manage-student-groups', component: ManageStudentGroups },
    ]
  }
];