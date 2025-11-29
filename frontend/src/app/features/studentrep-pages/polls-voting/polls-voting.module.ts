import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PollsVotingRoutingModule } from './polls-voting-routing.module';
import { PollsVoting } from './polls-voting';

@NgModule({
  declarations: [],
  imports: [CommonModule, PollsVotingRoutingModule, PollsVoting],
})
export class PollsVotingModule {}
