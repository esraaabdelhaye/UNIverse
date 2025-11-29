import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { Schedule } from './schedule';

const routes: Routes = [
  {
    path: '',
    component: Schedule,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ScheduleRoutingModule {}
