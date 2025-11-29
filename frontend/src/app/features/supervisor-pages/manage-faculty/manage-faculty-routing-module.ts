import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ManageFaculty } from './manage-faculty';

const routes: Routes = [
  {
    path: '',
    component: ManageFaculty,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ManageFacultyRoutingModule {}
