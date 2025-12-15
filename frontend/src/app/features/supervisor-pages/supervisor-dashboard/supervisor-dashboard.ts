import { Component, OnInit, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../core/services/auth.service';
import { SupervisorService, PerformanceMetrics } from '../../../core/services/supervisor.service';
import { DoctorService, FacultyItem } from '../../../core/services/doctor.service';

interface StatCard {
  label: string;
  value: number;
  icon: string;
  color: string;
}

interface Alert {
  type: 'warning' | 'info' | 'success';
  title: string;
  text: string;
  actionText: string;
  actionRoute?: string;
}

interface FacultyChange {
  name: string;
  role: string;
  avatar: string;
  id?: string;
}

interface PerformanceItem {
  title: string;
  description: string;
  reportType?: string;
}

@Component({
  selector: 'app-supervisor-dashboard',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, CommonModule],
  templateUrl: './supervisor-dashboard.html',
  styleUrl: './supervisor-dashboard.css',
})
export class SupervisorDashboard implements OnInit {
  private router = inject(Router);
  private authService = inject(AuthService);
  private supervisorService = inject(SupervisorService);
  private doctorService = inject(DoctorService);

  // Stats cards
  stats: StatCard[] = [];

  // Alerts
  courseAlerts: Alert[] = [];
  timetableAlerts: Alert[] = [];

  // Faculty
  recentFacultyChanges: FacultyChange[] = [];

  // Performance
  performanceMetrics: PerformanceItem[] = [];

  isLoading: boolean = false;

  ngOnInit(): void {
    this.loadDashboardData();
  }

  loadDashboardData(): void {
    this.isLoading = true;

    // Load performance metrics
    this.supervisorService.getSystemPerformance().subscribe({
      next: (res: any) => {
        this.isLoading = false;
        if (res.data) {
          const metrics: PerformanceMetrics = res.data;

          // Build stats cards from metrics
          this.stats = [
            { label: 'Total Users', value: metrics.totalUsers, icon: 'group', color: 'blue' },
            { label: 'Active Courses', value: metrics.activeCourses, icon: 'school', color: 'green' },
            { label: 'Pending Approvals', value: metrics.pendingApprovals, icon: 'pending_actions', color: 'orange' },
            { label: 'Publications', value: metrics.publicationsCount, icon: 'article', color: 'purple' },
          ];

          // Build alerts from system alerts
          this.courseAlerts = [];
          this.timetableAlerts = [];

          if (metrics.pendingApprovals > 0) {
            this.courseAlerts.push({
              type: 'warning',
              title: 'Pending Course Approvals',
              text: `${metrics.pendingApprovals} course(s) require your approval.`,
              actionText: 'Review Now',
              actionRoute: '/supervisor-dashboard/course-availability'
            });
          }

          if (metrics.activeCourses === 0) {
            this.courseAlerts.push({
              type: 'info',
              title: 'No Active Courses',
              text: 'There are no active courses for this semester.',
              actionText: 'Add Course',
              actionRoute: '/supervisor-dashboard/course-availability'
            });
          } else {
            this.courseAlerts.push({
              type: 'success',
              title: 'Courses Active',
              text: `${metrics.activeCourses} course(s) are currently running.`,
              actionText: 'View All',
              actionRoute: '/supervisor-dashboard/course-availability'
            });
          }

          this.timetableAlerts.push({
            type: 'info',
            title: 'Timetable Status',
            text: `Average generation time: ${metrics.timetableGenerationTime || 'N/A'}`,
            actionText: 'Generate New',
            actionRoute: '/supervisor-dashboard/generate-timetable'
          });

          if (metrics.resourceConflictPercentage > 0) {
            this.timetableAlerts.push({
              type: 'warning',
              title: 'Resource Conflicts',
              text: `${metrics.resourceConflictPercentage}% of slots have conflicts.`,
              actionText: 'Resolve',
              actionRoute: '/supervisor-dashboard/generate-timetable'
            });
          }

          // Build performance metrics list
          this.performanceMetrics = [
            { title: 'Student Feedback Report', description: `Avg Rating: ${metrics.avgStudentFeedback?.toFixed(1) || 'N/A'}/5`, reportType: 'feedback' },
            { title: 'Course Success Rate', description: `${metrics.courseSuccessRate?.toFixed(1) || 'N/A'}% completion`, reportType: 'success' },
            { title: 'System Uptime', description: `${metrics.systemUptimePercentage?.toFixed(2) || 'N/A'}% uptime`, reportType: 'uptime' },
          ];
        }
      },
      error: (err: any) => {
        this.isLoading = false;
        console.error('Failed to load performance metrics', err);
        // Use fallback data
        this.stats = [
          { label: 'Total Users', value: 0, icon: 'group', color: 'blue' },
          { label: 'Active Courses', value: 0, icon: 'school', color: 'green' },
          { label: 'Pending Approvals', value: 0, icon: 'pending_actions', color: 'orange' },
          { label: 'Publications', value: 0, icon: 'article', color: 'purple' },
        ];
      }
    });

    // Load recent faculty
    this.doctorService.getAllDoctors(0, 3).subscribe({
      next: (res: any) => {
        let doctors: FacultyItem[] = [];
        if (res.data && res.data.content) {
          doctors = res.data.content;
        } else if (Array.isArray(res.data)) {
          doctors = res.data.slice(0, 3);
        }

        this.recentFacultyChanges = doctors.map((dto: FacultyItem) => ({
          name: dto.fullName,
          role: dto.specialization || 'Faculty',
          avatar: 'https://ui-avatars.com/api/?name=' + encodeURIComponent(dto.fullName) + '&background=random',
          id: dto.doctorId
        }));
      },
      error: (err: any) => console.error('Failed to load faculty', err)
    });
  }

  getStatIconClass(color: string): string {
    return `stat-icon stat-icon-${color}`;
  }

  getAlertClass(type: string): string {
    return `alert alert-${type}`;
  }

  handleAlertAction(alert: Alert): void {
    if (alert.actionRoute) {
      this.router.navigate([alert.actionRoute]);
    }
  }

  viewAllCourses(): void {
    this.router.navigate(['/supervisor-dashboard/course-availability']);
  }

  generateNewTimetable(): void {
    this.router.navigate(['/supervisor-dashboard/generate-timetable']);
  }

  viewFacultyDetails(faculty: FacultyChange): void {
    this.router.navigate(['/supervisor-dashboard/manage-faculty']);
  }

  viewAllFaculty(): void {
    this.router.navigate(['/supervisor-dashboard/manage-faculty']);
  }

  downloadReport(performance: PerformanceItem): void {
    alert(`Downloading ${performance.title}...`);
  }

  exportDashboardData(): void {
    alert('Exporting dashboard data...');
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
