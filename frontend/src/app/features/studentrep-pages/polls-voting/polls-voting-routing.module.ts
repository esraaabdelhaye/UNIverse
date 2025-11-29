import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PollsVoting } from './polls-voting';

const routes: Routes = [
  {
    path: '',
    component: PollsVoting,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PollsVotingRoutingModule {}
