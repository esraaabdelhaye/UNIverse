import { Routes } from '@angular/router';

export const routes: Routes = [
  // AUTH Routes
  {
    path: '',
    loadComponent: () => import('./features/auth/pages/login/login').then((m) => m.Login),
  },
  {
    path: 'choose-account',
    loadComponent: () =>
      import('./features/auth/pages/choose-account/choose-account').then((m) => m.ChooseAccount),
  },
  {
    path: 'create-student-account',
    loadComponent: () =>
      import('./features/auth/pages/create-student-account/create-student-account').then(
        (m) => m.CreateStudentAccount
      ),
  },
  {
    path: 'create-ta-account',
    loadComponent: () =>
      import('./features/auth/pages/create-ta-account/create-ta-account').then(
        (m) => m.CreateTaAccount
      ),
  },
  {
    path: 'create-supervisor-account',
    loadComponent: () =>
      import('./features/auth/pages/create-supervisor-account/create-supervisor-account').then(
        (m) => m.CreateSupervisorAccount
      ),
  },

  // DOCTOR Routes
  {
    path: 'doctor-dashboard',
    loadComponent: () =>
      import('./features/doctor-pages/doctor-dashboard/doctor-dashboard').then(
        (m) => m.DoctorDashboard
      ),
  },
  {
    path: 'doctor-dashboard/upload-material',
    loadComponent: () =>
      import('./features/doctor-pages/upload-material/upload-material').then(
        (m) => m.UploadMaterial
      ),
  },
  {
    path: 'doctor-dashboard/course-students',
    loadComponent: () =>
      import('./features/doctor-pages/course-students/course-students').then(
        (m) => m.CourseStudents
      ),
  },
  {
    path: 'doctor-dashboard/view-submissions',
    loadComponent: () =>
      import('./features/doctor-pages/view-submissions/view-submissions').then(
        (m) => m.ViewSubmissions
      ),
  },
  {
    path: 'doctor-dashboard/announcements',
    loadComponent: () =>
      import('./features/doctor-pages/course-announcements/course-announcements').then(
        (m) => m.Announcements
      ),
  },

  // STUDENT Routes
  {
    path: 'student-dashboard',
    loadComponent: () =>
      import('./features/student-pages/student-dashboard/student-dashboard').then(
        (m) => m.StudentDashboard
      ),
  },
  {
    path: 'student-dashboard/my-courses',
    loadComponent: () =>
      import('./features/student-pages/my-courses/my-courses').then((m) => m.MyCourses),
  },
  {
    path: 'student-dashboard/submit-assignments',
    loadComponent: () =>
      import('./features/student-pages/submit-assignments/submit-assignments').then(
        (m) => m.SubmitAssignments
      ),
  },
  {
    path: 'student-dashboard/view-grades',
    loadComponent: () =>
      import('./features/student-pages/view-grades/view-grades').then((m) => m.ViewGrades),
  },
  {
    path: 'student-dashboard/view-materials',
    loadComponent: () =>
      import('./features/student-pages/view-materials/view-materials').then((m) => m.ViewMaterials),
  },
  {
    path: 'student-dashboard/view-announcements',
    loadComponent: () =>
      import('./features/student-pages/view-announcements/view-announcements')
        .then(m => m.ViewAnnouncements),
  },

  // SUPERVISOR Routes
  {
    path: 'supervisor-dashboard',
    loadComponent: () =>
      import('./features/supervisor-pages/supervisor-dashboard/supervisor-dashboard').then(
        (m) => m.SupervisorDashboard
      ),
  },
  {
    path: 'supervisor-dashboard/manage-faculty',
    loadComponent: () =>
      import('./features/supervisor-pages/manage-faculty/manage-faculty').then(
        (m) => m.ManageFaculty
      ),
  },
  {
    path: 'supervisor-dashboard/course-availability',
    loadComponent: () =>
      import('./features/supervisor-pages/course-availability/course-availability').then(
        (m) => m.CourseAvailability
      ),
  },
  {
    path: 'supervisor-dashboard/generate-timetable',
    loadComponent: () =>
      import('./features/supervisor-pages/generate-timetable/generate-timetable').then(
        (m) => m.GenerateTimetable
      ),
  },

  // 404 Fallback
  {
    path: '**',
    redirectTo: '',
  },
];
