import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MyCoursesRoutingModule } from './my-courses-routing-module';
import { MyCourses } from './my-courses';

@NgModule({
  declarations: [],
  imports: [CommonModule, MyCoursesRoutingModule, MyCourses],
})
export class MyCoursesModule {}
