import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';
import { AnnouncementService } from '../../../core/services/announcement.service';
import { StudentService } from '../../../core/services/student.service';

interface Announcement {
  id: number;
  announcementId?: string;
  title: string;
  message: string;
  content?: string;
  course: string;
  courseCode: string;
  createdDate: string;
  createdAt?: string;
  author: string;
  createdBy?: string;
  visibility?: string;
  status?: string;
}

interface CourseInfo {
  courseCode: string;
  course: string;
}

@Component({
  selector: 'app-view-announcements',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive, FormsModule],
  templateUrl: './view-announcements.html',
  styleUrl: './view-announcements.css',
})
export class ViewAnnouncements implements OnInit {
  private router = inject(Router);
  private authService = inject(AuthService);
  private announcementService = inject(AnnouncementService);
  private studentService = inject(StudentService);

  announcements: Announcement[] = [];
  filteredAnnouncements: Announcement[] = [];
  uniqueCourses: CourseInfo[] = [];

  searchTerm = '';
  selectedCourseFilter = '';
  selectedDateFilter = '';

  currentUser: any;

  isLoading = true;
  errorMessage = '';
  successMessage = '';

  ngOnInit() {
    this.currentUser = this.authService.getCurrentUser();

    if (!this.currentUser) {
      this.router.navigate(['/login']);
      return;
    }

    this.loadAnnouncements();
  }

  loadAnnouncements() {
    this.isLoading = true;
    this.errorMessage = '';
    this.successMessage = '';
    this.announcements = [];

    const studentId = parseInt(this.currentUser.studentId || this.currentUser.id);

    this.studentService.getStudentCourses(studentId).subscribe({
      next: (courseResponse) => {
        if (courseResponse.success && courseResponse.data) {
          const courses = courseResponse.data;
          const coursesArray = Array.isArray(courses)
            ? courses
            : courses
              ? [courses]
              : [];

          if (coursesArray.length === 0) {
            this.isLoading = false;
            this.errorMessage = 'You are not enrolled in any courses';
            return;
          }

          console.log('Loaded student courses:', coursesArray);

          let loadedCount = 0;
          coursesArray.forEach((course: any) => {
            const courseCode = course.courseCode || course.code;

            if (!courseCode) {
              console.warn('Course missing courseCode:', course);
              loadedCount++;
              if (loadedCount === coursesArray.length) {
                this.updateUniqueCourses();
                this.filterAnnouncements();
                this.isLoading = false;
              }
              return;
            }

            console.log(`Fetching announcements for course: ${courseCode}`);

            const courseTitle = course.courseTitle || course.name || courseCode;

            this.announcementService.getAnnouncementsByCoures({
              courseCode: courseCode,
              courseTitle: courseTitle
            }).subscribe({
              next: (annResponse) => {
                console.log(`Announcements for ${courseCode}:`, annResponse);

                if (annResponse.success && annResponse.data) {
                  const annData = annResponse.data;
                  const annArray = Array.isArray(annData)
                    ? annData
                    : annData
                      ? [annData]
                      : [];

                  annArray.forEach((ann: any) => {
                    const announcement: Announcement = {
                      id: ann.announcementId || ann.id || Math.random(),
                      announcementId: String(ann.announcementId || ann.id),
                      title: ann.title || 'Untitled',
                      message: ann.content || ann.message || '',
                      content: ann.content || ann.message || '',
                      course: courseTitle,
                      courseCode: ann.courseCode || courseCode,
                      createdDate: ann.createdDate || ann.createdAt || new Date().toISOString(),
                      createdAt: ann.createdDate || ann.createdAt || new Date().toISOString(),
                      author: ann.createdBy || 'Unknown',
                      createdBy: ann.createdBy || 'Unknown',
                      visibility: ann.visibility || 'public',
                      status: ann.status || 'ACTIVE',
                    };

                    this.announcements.push(announcement);
                  });

                  this.successMessage = `Loaded announcements for ${courseCode}`;
                  console.log(`Total announcements loaded: ${this.announcements.length}`);
                }

                loadedCount++;
                if (loadedCount === coursesArray.length) {
                  // All courses processed
                  this.updateUniqueCourses();
                  this.filterAnnouncements();
                  this.isLoading = false;
                  console.log('All announcements loaded successfully');
                }
              },
              error: (err) => {
                console.error(`Error loading announcements for ${courseCode}:`, err);
                loadedCount++;
                if (loadedCount === coursesArray.length) {
                  this.updateUniqueCourses();
                  this.filterAnnouncements();
                  this.isLoading = false;

                  if (this.announcements.length === 0) {
                    this.errorMessage = 'No announcements found for your courses';
                  }
                }
              }
            });
          });
        } else {
          this.isLoading = false;
          this.errorMessage = 'Failed to load your courses';
        }
      },
      error: (err) => {
        console.error('Error loading student courses:', err);
        this.isLoading = false;
        this.errorMessage = 'Failed to load announcements. Please try again.';
      }
    });
  }

  updateUniqueCourses() {
    const courses = new Map<string, CourseInfo>();

    this.announcements.forEach(announcement => {
      if (!courses.has(announcement.courseCode)) {
        courses.set(announcement.courseCode, {
          courseCode: announcement.courseCode,
          course: announcement.course,
        });
      }
    });

    this.uniqueCourses = Array.from(courses.values()).sort((a, b) =>
      a.courseCode.localeCompare(b.courseCode)
    );

    console.log('Unique courses:', this.uniqueCourses);
  }

  filterAnnouncements() {
    this.filteredAnnouncements = this.announcements.filter(announcement => {
      const matchesSearch = this.matchesSearch(announcement);
      const matchesCourse = this.matchesCourseFilter(announcement);
      const matchesDate = this.matchesDateFilter(announcement);

      return matchesSearch && matchesCourse && matchesDate;
    });

    this.filteredAnnouncements.sort((a, b) => {
      const dateA = new Date(a.createdDate).getTime();
      const dateB = new Date(b.createdDate).getTime();
      return dateB - dateA;
    });

    console.log('Filtered announcements:', this.filteredAnnouncements);
  }

  private matchesSearch(announcement: Announcement): boolean {
    if (!this.searchTerm || this.searchTerm.trim() === '') {
      return true;
    }

    const query = this.searchTerm.toLowerCase().trim();
    return (
      announcement.title.toLowerCase().includes(query) ||
      announcement.message.toLowerCase().includes(query) ||
      announcement.author.toLowerCase().includes(query) ||
      announcement.courseCode.toLowerCase().includes(query)
    );
  }

  private matchesCourseFilter(announcement: Announcement): boolean {
    if (!this.selectedCourseFilter) {
      return true;
    }

    return announcement.course === this.selectedCourseFilter;
  }

  private matchesDateFilter(announcement: Announcement): boolean {
    if (!this.selectedDateFilter) {
      return true;
    }

    const announcementDate = new Date(announcement.createdDate);
    const now = new Date();
    const today = new Date(now.getFullYear(), now.getMonth(), now.getDate());

    switch (this.selectedDateFilter) {
      case 'today':
        const todayStart = new Date(today);
        const todayEnd = new Date(today);
        todayEnd.setDate(todayEnd.getDate() + 1);
        return announcementDate >= todayStart && announcementDate < todayEnd;

      case 'week':
        const weekAgo = new Date(today);
        weekAgo.setDate(today.getDate() - 7);
        return announcementDate >= weekAgo;

      case 'month':
        const monthAgo = new Date(today);
        monthAgo.setMonth(today.getMonth() - 1);
        return announcementDate >= monthAgo;

      default:
        return true;
    }
  }

  onSearchChange() {
    this.filterAnnouncements();
  }

  onCourseFilterChange() {
    this.filterAnnouncements();
  }

  onDateFilterChange() {
    this.filterAnnouncements();
  }

  clearFilters() {
    this.searchTerm = '';
    this.selectedCourseFilter = '';
    this.selectedDateFilter = '';
    this.filterAnnouncements();
  }

  refreshAnnouncements() {
    this.loadAnnouncements();
  }

  formatDate(dateString: string): string {
    try {
      const date = new Date(dateString);
      return date.toLocaleDateString('en-US', {
        year: 'numeric',
        month: 'short',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
      });
    } catch {
      return dateString;
    }
  }

  getRelativeTime(dateString: string): string {
    try {
      const date = new Date(dateString);
      const now = new Date();
      const diffMs = now.getTime() - date.getTime();
      const diffMins = Math.floor(diffMs / 60000);
      const diffHours = Math.floor(diffMs / 3600000);
      const diffDays = Math.floor(diffMs / 86400000);

      if (diffMins < 1) return 'Just now';
      if (diffMins < 60) return `${diffMins}m ago`;
      if (diffHours < 24) return `${diffHours}h ago`;
      if (diffDays < 7) return `${diffDays}d ago`;
      if (diffDays < 30) return `${Math.floor(diffDays / 7)}w ago`;

      return this.formatDate(dateString);
    } catch {
      return dateString;
    }
  }

  navigateToCourse(courseCode: string) {
    this.router.navigate(['/student-dashboard/my-courses'], {
      queryParams: { course: courseCode }
    });
  }

  getStatusBadgeColor(status: string): string {
    switch (status?.toUpperCase()) {
      case 'ACTIVE':
        return 'badge-active';
      case 'ARCHIVED':
        return 'badge-archived';
      case 'DRAFT':
        return 'badge-draft';
      default:
        return 'badge-active';
    }
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
