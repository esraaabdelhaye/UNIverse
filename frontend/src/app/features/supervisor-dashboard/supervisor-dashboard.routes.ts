import { Routes } from '@angular/router';
import { SupervisorDashboardComponent } from './supervisor-dashboard.component';

import { HomeComponent } from './pages/home/home.component';
import { ManageFacultyComponent } from './pages/manage-faculty/manage-faculty.component';
import { ManageCoursesComponent } from './pages/manage-courses/manage-courses.component';
import { GenTimetableComponent } from './pages/gen-timetable/gen-timetable.component';
import { ReviewPerformanceComponent } from './pages/review-performance/review-performance.component';

export const SUPERVISOR_ROUTES: Routes = [
  {
    path: '',
    component: SupervisorDashboardComponent,
    children: [
      { path: '', redirectTo: 'home', pathMatch: 'full' }, // الافتراضية
      { path: 'home', component: HomeComponent },
      { path: 'faculty', component: ManageFacultyComponent },
      { path: 'courses', component: ManageCoursesComponent },
      { path: 'timetable', component: GenTimetableComponent },
      { path: 'performance', component: ReviewPerformanceComponent },
    ]
  }
];