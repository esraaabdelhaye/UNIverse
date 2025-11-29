import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AddAssignment } from './add-assignment';

const routes: Routes = [
  {
    path: '',
    component: AddAssignment,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AddAssignmentRoutingModule {}
