import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { MyCourses } from './my-courses';

const routes: Routes = [
  {
    path: '',
    component: MyCourses,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MyCoursesRoutingModule {}
