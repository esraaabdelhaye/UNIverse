import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ReviewPerformance } from './review-performance';

const routes: Routes = [
  {
    path: '',
    component: ReviewPerformance,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ReviewPerformanceRoutingModule {}
