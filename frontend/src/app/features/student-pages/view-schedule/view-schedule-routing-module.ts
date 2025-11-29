import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ViewSchedule } from './view-schedule';

const routes: Routes = [
  {
    path: '',
    component: ViewSchedule,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ViewScheduleRoutingModule {}
