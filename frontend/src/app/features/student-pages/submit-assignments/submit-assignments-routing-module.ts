import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { SubmitAssignments } from './submit-assignments';

const routes: Routes = [
  {
    path: '',
    component: SubmitAssignments,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SubmitAssignmentsRoutingModule {}
