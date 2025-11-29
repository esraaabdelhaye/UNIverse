import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CourseAnnouncements } from './course-announcements';

const routes: Routes = [
  {
    path: '',
    component: CourseAnnouncements,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CourseAnnouncementsRoutingModule {}
