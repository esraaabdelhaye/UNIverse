import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RepDashBoard } from '../../rep-dash-board';
import { DashBoard } from '../dash-board/dash-board';
import { ManageStudentGroups } from '../manage-student-groups/manage-student-groups';

// export const routes: Routes = [
//   {
//     path: '',
//     component: RepDashBoard,
//     children: [
//       { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
//       { path: 'dashboard', component: DashBoard },
//       { path: 'manage-student-groups', component: ManageStudentGroups },
//     ]
//   }
// ];

// @NgModule({
//   imports: [RouterModule.forChild(routes)],
//   exports: [RouterModule]
// })
export class RedDashBoardRoutingModule {}