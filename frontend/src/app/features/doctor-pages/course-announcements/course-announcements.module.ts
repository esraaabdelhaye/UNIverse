import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CourseAnnouncementsRoutingModule } from './course-announcements-routing.module';
import { CourseAnnouncements } from './course-announcements';

@NgModule({
  declarations: [],
  imports: [CommonModule, CourseAnnouncementsRoutingModule, CourseAnnouncements],
})
export class CourseAnnouncementsModule {}
