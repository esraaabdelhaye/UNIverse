import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReviewPerformanceRoutingModule } from './review-performance-routing-module';
import { ReviewPerformance } from './review-performance';

@NgModule({
  declarations: [],
  imports: [CommonModule, ReviewPerformanceRoutingModule, ReviewPerformance],
})
export class ReviewPerformanceModule {}
