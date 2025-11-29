import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { CourseAvailability } from './course-availability';

const routes: Routes = [
  {
    path: '',
    component: CourseAvailability,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CourseAvailabilityRoutingModule {}
