import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CourseAvailabilityRoutingModule } from './course-availability-routing-module';
import { CourseAvailability } from './course-availability';

@NgModule({
  declarations: [],
  imports: [CommonModule, CourseAvailabilityRoutingModule, CourseAvailability],
})
export class CourseAvailabilityModule {}
