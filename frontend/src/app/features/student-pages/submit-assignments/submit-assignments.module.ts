import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SubmitAssignmentsRoutingModule } from './submit-assignments-routing-module';
import { SubmitAssignments } from './submit-assignments';

@NgModule({
  declarations: [],
  imports: [CommonModule, SubmitAssignmentsRoutingModule, SubmitAssignments],
})
export class SubmitAssignmentsModule {}
