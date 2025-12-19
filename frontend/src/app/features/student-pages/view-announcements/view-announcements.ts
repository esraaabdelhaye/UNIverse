import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';
import { AnnouncementService } from '../../../core/services/announcement.service';
import { CourseService } from '../../../core/services/course.service';
import { StudentService } from '../../../core/services/student.service';

interface Announcement {
  id: number;
  title: string;
  message: string;
  course: string;
  courseCode: string;
  createdDate: string;
  author: string;
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
  uniqueCourses: Array<{ courseCode: string; course: string }> = [];

  searchTerm = '';
  selectedCourseFilter = '';
  selectedDateFilter = '';

  currentUser: any;

  ngOnInit() {
    this.currentUser = this.authService.getCurrentUser();
    this.loadAnnouncements();
  }

  loadAnnouncements() {
    // Currently this approach loade all announcements for all courses the student is enrolled in
    // This is not the most efficient way but works for now
    // for future updates use pagination or lazy loading
    // for better performance
    const studentId = parseInt(this.currentUser.studentId || this.currentUser.id);
    // Loading course first
    this.studentService.getStudentCourses(studentId).subscribe({
      next: (response) => {
        const courses = response.data;
        const coursesArray = Array.isArray(courses) ? courses : courses ? [courses] : [];
        // Now load announcements for each course
        coursesArray.forEach((course) => {
          this.announcementService.getAnnouncementsByCourse(course).subscribe({
            next: (annResponse) => {
              if (annResponse.success && annResponse.data) {
                const annData = annResponse.data;
                const annArray = Array.isArray(annData) ? annData : annData ? [annData] : [];
                annArray.forEach((ann: any) => {
                  this.announcements.push({
                    id: ann.announcementId,
                    title: ann.title,
                    message: ann.content,
                    course: course.courseTitle,
                    courseCode: ann.courseCode,
                    createdDate: ann.createdDate,
                    author: ann.createdBy || 'Unknown',
                  });
                });
                // Update filtered announcements and unique courses after adding new data
                this.updateUniqueCourses();
                this.filteredAnnouncements = [...this.announcements];
              }
            },
          });
        });
      },
    });
  }

  updateUniqueCourses() {
    const courses = new Map();
    console.log('Updating unique courses from announcements');
    console.log(this.announcements);
    this.announcements.forEach((announcement) => {
      if (!courses.has(announcement.courseCode)) {
        courses.set(announcement.courseCode, {
          courseCode: announcement.courseCode,
          course: announcement.course,
        });
      }
    });
    this.uniqueCourses = Array.from(courses.values());
  }

  filterAnnouncements() {
    this.filteredAnnouncements = this.announcements.filter((announcement) => {
      const matchesSearch =
        announcement.title.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        announcement.message.toLowerCase().includes(this.searchTerm.toLowerCase());

      const matchesCourse =
        !this.selectedCourseFilter || announcement.course === this.selectedCourseFilter;
      const matchesDate = this.matchesDateFilter(announcement.createdDate);

      return matchesSearch && matchesCourse && matchesDate;
    });
  }

  matchesDateFilter(createdDate: string): boolean {
    if (!this.selectedDateFilter) return true;

    const announcementDate = new Date(createdDate);
    const now = new Date();
    const today = new Date(now.getFullYear(), now.getMonth(), now.getDate());

    switch (this.selectedDateFilter) {
      case 'today':
        return announcementDate >= today;
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

  clearFilters() {
    this.searchTerm = '';
    this.selectedCourseFilter = '';
    this.selectedDateFilter = '';
    this.filteredAnnouncements = [...this.announcements];
  }

  onDateFilterChange() {
    this.filterAnnouncements();
  }

  setDateFilter(value: string) {
    this.selectedDateFilter = value;
    this.filterAnnouncements();
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
