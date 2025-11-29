import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AddGrades } from './add-grades';

const routes: Routes = [
  {
    path: '',
    component: AddGrades,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AddGradesRoutingModule {}
