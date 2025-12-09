import { Routes } from '@angular/router';
import { Login } from './features/auth/pages/login/login';
import { ChooseAccount } from './features/auth/pages/choose-account/choose-account';
import { CreateStudentAccount } from './features/auth/pages/create-student-account/create-student-account';
import { CreateSupervisorAccount } from './features/auth/pages/create-supervisor-account/create-supervisor-account';
import { CreateTaAccount } from './features/auth/pages/create-ta-account/create-ta-account';

import { StudentDashboardComponent } from './features/student-pages/student-dashboard/student-dashboard';
import { StudentCoursesComponent } from './features/student-pages/student-courses/student-courses';
import { StudentAssignmentsComponent } from './features/student-pages/student-assignments/student-assignments';
import { StudentSubmitComponent } from './features/student-pages/student-submit/student-submit';
import { StudentMaterialsComponent } from './features/student-pages/student-materials/student-materials';

import { ProfessorDashboardComponent } from './features/doctor-pages/professor-dashboard/professor-dashboard';
import { ProfessorCoursesComponent } from './features/doctor-pages/professor-courses/professor-courses';
import { ProfessorGradeComponent } from './features/doctor-pages/professor-grade/professor-grade';
import { ProfessorMaterialsComponent } from './features/doctor-pages/professor-materials/professor-materials';
import { ProfessorAssignmentComponent } from './features/doctor-pages/professor-assignment/professor-assignment';

import { AdminDashboardComponent } from './features/supervisor-pages/admin-dashboard/admin-dashboard';
import { AdminCoursesComponent } from './features/supervisor-pages/admin-courses/admin-courses';
import { AdminFacultyComponent } from './features/supervisor-pages/admin-faculty/admin-faculty';
import { AdminTimetableComponent } from './features/supervisor-pages/admin-timetable/admin-timetable';

import { TaDashboardComponent } from './features/ta-pages/ta-dashboard/ta-dashboard';
import { TaQueriesComponent } from './features/ta-pages/ta-queries/ta-queries';
import { TaAddGradesComponent } from './features/ta-pages/ta-add-grades/ta-add-grades';
import { TaSubmissionsComponent } from './features/ta-pages/ta-submissions/ta-submissions';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: Login },
  { path: 'choose-account', component: ChooseAccount },
  { path: 'create-student-account', component: CreateStudentAccount },
  { path: 'create-supervisor-account', component: CreateSupervisorAccount },
  { path: 'create-ta-account', component: CreateTaAccount },

  { path: 'student-dashboard', component: StudentDashboardComponent },
  { path: 'student-courses', component: StudentCoursesComponent },
  { path: 'student-assignments', component: StudentAssignmentsComponent },
  { path: 'student-submit', component: StudentSubmitComponent },
  { path: 'student-materials', component: StudentMaterialsComponent },

  { path: 'professor-dashboard', component: ProfessorDashboardComponent },
  { path: 'professor-courses', component: ProfessorCoursesComponent },
  { path: 'professor-grade', component: ProfessorGradeComponent },
  { path: 'professor-materials', component: ProfessorMaterialsComponent },
  { path: 'professor-assignment', component: ProfessorAssignmentComponent },

  { path: 'admin-dashboard', component: AdminDashboardComponent },
  { path: 'admin-courses', component: AdminCoursesComponent },
  { path: 'admin-faculty', component: AdminFacultyComponent },
  { path: 'admin-timetable', component: AdminTimetableComponent },

  { path: 'ta-dashboard', component: TaDashboardComponent },
  { path: 'ta-queries', component: TaQueriesComponent },
  { path: 'ta-addgrades', component: TaAddGradesComponent },
  { path: 'ta-submissions', component: TaSubmissionsComponent },

  { path: '**', redirectTo: '/login' }
];
