import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ViewGrades } from './view-grades';

const routes: Routes = [
  {
    path: '',
    component: ViewGrades,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ViewGradesRoutingModule {}
