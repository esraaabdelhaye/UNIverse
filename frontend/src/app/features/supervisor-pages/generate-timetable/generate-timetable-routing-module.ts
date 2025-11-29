import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { GenerateTimetable } from './generate-timetable';

const routes: Routes = [
  {
    path: '',
    component: GenerateTimetable,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class GenerateTimetableRoutingModule {}
