import { Component, OnInit, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../core/services/auth.service';

interface StatCard {
  label: string;
  value: number;
  icon: string;
  color: 'blue' | 'green' | 'yellow' | 'red';
}

interface Alert {
  title: string;
  text: string;
  type: 'blue' | 'yellow' | 'red' | 'gray';
  actionText?: string;
  actionLink?: string;
}

interface FacultyItem {
  id: string;
  name: string;
  role: string;
  avatar: string;
  recentAction?: string;
}

interface PerformanceItem {
  id: string;
  title: string;
  description: string;
  downloadUrl?: string;
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

  // Statistics
  stats: StatCard[] = [
    {
      label: 'Total Users',
      value: 1254,
      icon: 'group',
      color: 'blue',
    },
    {
      label: 'Active Courses',
      value: 86,
      icon: 'school',
      color: 'green',
    },
    {
      label: 'Pending Approvals',
      value: 12,
      icon: 'pending_actions',
      color: 'yellow',
    },
    {
      label: 'System Alerts',
      value: 3,
      icon: 'error',
      color: 'red',
    },
  ];

  // Course Availability Alerts
  courseAlerts: Alert[] = [
    {
      title: 'Upcoming Course Closures',
      text: 'CS400 (Advanced AI) closing for enrollment in 3 days.',
      type: 'blue',
      actionText: 'Review Details',
    },
    {
      title: 'Course Capacity Alert',
      text: 'MATH201 (Calculus II) is 90% full. Consider adding another section.',
      type: 'yellow',
      actionText: 'Adjust Capacity',
    },
  ];

  // Timetable Alerts
  timetableAlerts: Alert[] = [
    {
      title: 'Timetable Conflicts (2)',
      text: 'CS101 & ENG203 clash on Mon 10 AM. Dr. Evans double-booked.',
      type: 'red',
      actionText: 'Resolve Conflicts',
    },
    {
      title: 'Last Generated: Oct 26, 2023',
      text: 'Current timetable is active for Fall Semester.',
      type: 'gray',
      actionText: 'View Current Timetable',
    },
  ];

  // Faculty List
  recentFacultyChanges: FacultyItem[] = [
    {
      id: '1',
      name: 'Alice Johnson',
      role: 'Senior Lecturer - Recently Promoted',
      avatar:
        'https://lh3.googleusercontent.com/aida-public/AB6AXuB3YHbEbU8SfmFn-oiqCV9_9rifN3FvAKK74-w9szwjG03WDWaZxLHLEt5ulxW4Iw6geddE7lDpg_wOUw0ZqPZMC8eFGLDRa5nl6Z8By1PyDy4uW6VtUGsrxQIwn6JHVgczTcJFMVjA13g56JGKU0xnwLHSCCekH3RagiV_GWlkWSSvE8DD86sOKb_Mtgf21I_RFzDelsA-LFCJMvNq0LRhANlqvShCpm3ofkCw66SFJjx0eZB13uoUgPad_7TmrkZ36pL4B8oG_wc',
      recentAction: 'Recently Promoted',
    },
    {
      id: '2',
      name: 'Dr. Jane Smith',
      role: 'Professor - Updated Course HIS450',
      avatar:
        'https://lh3.googleusercontent.com/aida-public/AB6AXuBjNVIwYogETpglvkEd7kd4IK1K9-HPRNlkOroRXJaVZAv_Oxkr_flu4Oukr96laOwcdk0IardreU7Nw0DRGA4hyFZqrw2oklKovs9QaMzczv58zvToVUEkJR-BLly6VCWvf6XdifTblP-cki6h9MeI4IhtaKAapwoUL2mg4XHPX8L8OOrpKpUz_Rui88Te6_WWBWKph57tSD8S80N27dMPl1XkCzR5QruN3VqnEqVlMTHL-oBBCuyop1DFVx_vXZLpqS21JdDCXhU',
      recentAction: 'Updated Course HIS450',
    },
  ];

  // Performance Metrics
  performanceMetrics: PerformanceItem[] = [
    {
      id: '1',
      title: 'Q3 Student Feedback Report',
      description: 'Overall satisfaction at 85%.',
      downloadUrl: '/reports/q3-feedback.pdf',
    },
    {
      id: '2',
      title: 'Faculty Publication Rates',
      description: 'Average 2.3 publications per faculty this year.',
      downloadUrl: '/reports/publications.pdf',
    },
  ];

  ngOnInit(): void {
    this.loadDashboardData();
  }

  loadDashboardData(): void {
    console.log('Loading dashboard data...');
    // In a real application, you would fetch data from services here
    // this.dashboardService.getStats().subscribe(stats => this.stats = stats);
    // this.dashboardService.getCourseAlerts().subscribe(alerts => this.courseAlerts = alerts);
    // etc.
  }

  navigateToManageCourses(): void {
    console.log('Navigating to manage courses');
    this.router.navigate(['/supervisor-dashboard/course-availability']);
  }

  navigateToGenerateTimetable(): void {
    console.log('Navigating to generate timetable');
    this.router.navigate(['/supervisor-dashboard/generate-timetable']);
  }

  navigateToManageFaculty(): void {
    console.log('Navigating to manage faculty');
    this.router.navigate(['/supervisor-dashboard/manage-faculty']);
  }

  navigateToReviewPerformance(): void {
    console.log('Navigating to review performance');
    this.router.navigate(['/supervisor-dashboard/review-performance']);
  }

  reviewCourseDetails(alert: Alert): void {
    console.log('Reviewing course details:', alert.title);
    this.router.navigate(['/supervisor-dashboard/course-availability']);
  }

  adjustCapacity(alert: Alert): void {
    console.log('Adjusting capacity for course');
    // Implement capacity adjustment logic
  }

  resolveConflicts(alert: Alert): void {
    console.log('Resolving timetable conflicts');
    this.router.navigate(['/supervisor-dashboard/generate-timetable']);
  }

  viewCurrentTimetable(alert: Alert): void {
    console.log('Viewing current timetable');
    this.router.navigate(['/supervisor-dashboard/generate-timetable']);
  }

  handleAlertAction(alert: Alert): void {
    switch (alert.title) {
      case 'Upcoming Course Closures':
        this.reviewCourseDetails(alert);
        break;
      case 'Course Capacity Alert':
        this.adjustCapacity(alert);
        break;
      case 'Timetable Conflicts (2)':
        this.resolveConflicts(alert);
        break;
      case 'Last Generated: Oct 26, 2023':
        this.viewCurrentTimetable(alert);
        break;
      default:
        console.log('Alert action not mapped:', alert.title);
    }
  }

  viewAllCourses(): void {
    console.log('Viewing all courses');
    this.router.navigate(['/supervisor-dashboard/course-availability']);
  }

  generateNewTimetable(): void {
    console.log('Generating new timetable');
    this.router.navigate(['/supervisor-dashboard/generate-timetable']);
  }

  viewAllFaculty(): void {
    console.log('Viewing all faculty');
    this.router.navigate(['/supervisor-dashboard/manage-faculty']);
  }

  viewFacultyDetails(faculty: FacultyItem): void {
    console.log('Viewing faculty details for:', faculty.name);
    this.router.navigate(['/supervisor-dashboard/manage-faculty'], {
      queryParams: { id: faculty.id },
    });
  }

  accessPerformanceReports(): void {
    console.log('Accessing performance reports');
    this.router.navigate(['/supervisor-dashboard/review-performance']);
  }

  downloadReport(performance: PerformanceItem): void {
    console.log('Downloading report:', performance.title);
    if (performance.downloadUrl) {
      window.open(performance.downloadUrl, '_blank');
    }
  }

  refreshDashboard(): void {
    console.log('Refreshing dashboard');
    this.loadDashboardData();
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }

  getStatIconClass(color: string): string {
    return `stat-icon ${color}`;
  }

  getAlertClass(type: string): string {
    return `alert alert-${type}`;
  }

  getStatColor(index: number): string {
    const colors = ['blue', 'green', 'yellow', 'red'];
    return colors[index % colors.length];
  }
}
