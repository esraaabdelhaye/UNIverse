import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';
import { Announcement } from '../../../core/models/announcement.model';
import { Course } from '../../../core/models/course.model';
import { DoctorService } from '../../../core/services/doctor.service';
import { AnnouncementService } from '../../../core/services/announcement.service';
@Component({
  selector: 'app-announcements',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive, FormsModule],
  templateUrl: './course-announcements.html',
  styleUrl: './course-announcements.css',
})
export class Announcements implements OnInit {
  private router = inject(Router);
  private authService = inject(AuthService);
  private doctorService = inject(DoctorService);
  private announcementService = inject(AnnouncementService);

  courses: Course[] = [];
  announcements: Announcement[] = [];
  currentDoctor: any;

  selectedCourse = '';
  newTitle = '';
  newMessage = '';
  isPublishing = false;
  isLoading = false;

  ngOnInit() {
    this.currentDoctor = this.authService.getCurrentUser();
    this.loadCourses();
  }

  loadCourses() {
    this.isLoading = true;
    this.doctorService.getDoctorCourses(this.currentDoctor.doctorId).subscribe({
      next: (response) => {
        if (response.success && response.data) {
          const courses = response.data;
          this.courses = Array.isArray(courses) ? courses : courses ? [courses] : [];
          console.log('Loaded courses:', this.courses);
          this.isLoading = false;
          // load announcements after courses are loaded
          this.loadAnnouncements();
        }
      },
      error: (err) => {
        console.error('Could not load courses:', err);
        this.isLoading = false;
      },
    });
  }

  loadAnnouncements() {
    for (const course of this.courses) {
      this.announcementService.getAnnouncementsByCourse(course).subscribe({
        next: (response) => {
          if (response.success && response.data) {
            const anns = response.data;
            this.announcements.push(...(Array.isArray(anns) ? anns : anns ? [anns] : []));
            console.log('Loaded announcements:', this.announcements);
          }
        },
        error: (err) => {
          console.error('Could not load announcements:', err);
        },
      });
    }
  }

  publishAnnouncement() {
    // if (!this.selectedCourse || !this.newTitle || !this.newMessage) {
    //   alert('Please fill all required fields');
    //   return;
    // }
    // this.isPublishing = true;
    // setTimeout(() => {
    //   const newAnnouncement: Announcement = {
    //     id: this.announcements.length + 1,
    //     title: this.newTitle,
    //     message: this.newMessage,
    //     course: this.selectedCourse,
    //     status: 'published',
    //     createdDate: new Date().toLocaleDateString('en-US', {
    //       year: 'numeric',
    //       month: 'short',
    //       day: 'numeric',
    //     }),
    //   };
    //   this.announcements.unshift(newAnnouncement);
    //   this.resetForm();
    //   this.isPublishing = false;
    //   alert('Announcement published successfully!');
    // }, 1000);
  }

  deleteAnnouncement(announcement: Announcement) {
    if (confirm(`Delete announcement "${announcement.title}"?`)) {
      const index = this.announcements.indexOf(announcement);
      if (index > -1) {
        this.announcements.splice(index, 1);
      }
      this.announcementService.deleteAnnouncement(announcement.announcementId).subscribe({
        next: () => {
          console.log('Announcement deleted successfully');
        },
        error: (err) => {
          console.error('Error deleting announcement:', err);
        },
      });
    }
  }

  publishDraft(announcement: Announcement) {
    announcement.status = 'published';
    alert(`Announcement "${announcement.title}" published!`);
  }

  editAnnouncement(announcement: Announcement) {
    // this.selectedCourse = announcement.course;
    // this.newTitle = announcement.title;
    // this.newMessage = announcement.message;
    // this.deleteAnnouncement(announcement);
  }

  resetForm() {
    this.selectedCourse = '';
    this.newTitle = '';
    this.newMessage = '';
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}

// saveDraft() {
// if (!this.selectedCourse || !this.newTitle || !this.newMessage) {
//   alert('Please fill all fields before saving as draft');
//   return;
// }
// const newAnnouncement: Announcement = {
//   id: this.announcements.length + 1,
//   title: this.newTitle,
//   message: this.newMessage,
//   course: this.selectedCourse,
//   status: 'draft',
//   createdDate: new Date().toLocaleDateString('en-US', {
//     year: 'numeric',
//     month: 'short',
//     day: 'numeric',
//   }),
// };
// this.announcements.unshift(newAnnouncement);
// this.resetForm();
// alert('Announcement saved as draft!');
// }
