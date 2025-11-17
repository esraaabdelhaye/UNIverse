import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ManageStudentGroups } from '../manage-student-groups/manage-student-groups';
import { DashBoard } from '../dash-board/dash-board';
import { RepDashBoard } from '../../rep-dash-board';

export const routes: Routes = [
  {
    path: '',
    component: RepDashBoard,
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', component: DashBoard },
      { path: 'manage-student-groups', component: ManageStudentGroups },
      // { path: 'live-feed', component: LiveFeedComponent },
      // { path: 'polls-voting', component: PollsVotingComponent },
      // { path: 'events', component: EventsComponent },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RedDashBoardRoutingModule { }
