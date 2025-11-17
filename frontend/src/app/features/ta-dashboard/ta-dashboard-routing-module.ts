import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

import { TaDashboard } from './ta-dashboard';
import { UploadMaterials } from './pages/upload-materials/upload-materials';
import { AddGrades } from './pages/add-grades/add-grades';
import { AddAssignments } from './pages/add-assignments/add-assignments';
import { AnswerQuestions } from './pages/answer-questions/answer-questions';
import { ManageAnnouncements } from './pages/manage-announcements/manage-announcements';
import { Schedule } from './pages/schedule/schedule';
import { Logout } from './pages/logout/logout';
import { Dashboard } from './pages/dashboard/dashboard';

const routes: Routes = [
  {
    path: '',
    component: TaDashboard,
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full'},
      { path: 'dashboard', component: Dashboard},
      { path: 'upload-materials', component: UploadMaterials },
      { path: 'add-grades', component: AddGrades },
      { path: 'add-assignments', component: AddAssignments },
      { path: 'answer-questions', component: AnswerQuestions },
      { path: 'manage-announcements', component: ManageAnnouncements },
      { path: 'schedule', component: Schedule },
      { path: 'logout', component: Logout },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class TaDashboardRoutingModule {}
