import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';

interface Announcement {
  id: number;
  title: string;
  message: string;
  course: string;
  status: 'published' | 'draft';
  createdDate: string;
}

interface Course {
  id: number;
  code: string;
  name: string;
}

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

  courses: Course[] = [];
  announcements: Announcement[] = [];

  selectedCourse = '';
  newTitle = '';
  newMessage = '';
  isPublishing = false;

  ngOnInit() {
    this.loadCourses();
    this.loadAnnouncements();
  }

  loadCourses() {
    this.courses = [
      { id: 1, code: 'PHIL-301', name: 'Existentialism in Film' },
      { id: 2, code: 'HIST-212', name: 'Renaissance Art History' },
      { id: 3, code: 'LIT-405', name: 'Modernist Poetry' },
      { id: 4, code: 'PHIL-101', name: 'Introduction to Logic' },
    ];
  }

  loadAnnouncements() {
    this.announcements = [
      {
        id: 1,
        title: 'Essay 2 Deadline Reminder',
        message: "Don't forget to submit Essay 2 by Oct 28. Late submissions will incur a penalty.",
        course: 'PHIL-301',
        status: 'published',
        createdDate: 'Oct 26, 2023',
      },
      {
        id: 2,
        title: 'New Reading Material Available',
        message: "Check the Materials section for the new supplementary readings on Renaissance Art.",
        course: 'HIST-212',
        status: 'published',
        createdDate: 'Oct 25, 2023',
      },
      {
        id: 3,
        title: 'Office Hours Rescheduled',
        message: "Office hours this week have been moved to Wednesday 2-4 PM. Join via Zoom link in the course page.",
        course: 'LIT-405',
        status: 'draft',
        createdDate: 'Oct 24, 2023',
      },
    ];
  }

  publishAnnouncement() {
    if (!this.selectedCourse || !this.newTitle || !this.newMessage) {
      alert('Please fill all required fields');
      return;
    }

    this.isPublishing = true;

    setTimeout(() => {
      const newAnnouncement: Announcement = {
        id: this.announcements.length + 1,
        title: this.newTitle,
        message: this.newMessage,
        course: this.selectedCourse,
        status: 'published',
        createdDate: new Date().toLocaleDateString('en-US', {
          year: 'numeric',
          month: 'short',
          day: 'numeric',
        }),
      };

      this.announcements.unshift(newAnnouncement);
      this.resetForm();
      this.isPublishing = false;
      alert('Announcement published successfully!');
    }, 1000);
  }

  saveDraft() {
    if (!this.selectedCourse || !this.newTitle || !this.newMessage) {
      alert('Please fill all fields before saving as draft');
      return;
    }

    const newAnnouncement: Announcement = {
      id: this.announcements.length + 1,
      title: this.newTitle,
      message: this.newMessage,
      course: this.selectedCourse,
      status: 'draft',
      createdDate: new Date().toLocaleDateString('en-US', {
        year: 'numeric',
        month: 'short',
        day: 'numeric',
      }),
    };

    this.announcements.unshift(newAnnouncement);
    this.resetForm();
    alert('Announcement saved as draft!');
  }

  deleteAnnouncement(announcement: Announcement) {
    if (confirm(`Delete announcement "${announcement.title}"?`)) {
      const index = this.announcements.indexOf(announcement);
      if (index > -1) {
        this.announcements.splice(index, 1);
      }
    }
  }

  publishDraft(announcement: Announcement) {
    announcement.status = 'published';
    alert(`Announcement "${announcement.title}" published!`);
  }

  editAnnouncement(announcement: Announcement) {
    this.selectedCourse = announcement.course;
    this.newTitle = announcement.title;
    this.newMessage = announcement.message;
    this.deleteAnnouncement(announcement);
  }

  resetForm() {
    this.selectedCourse = '';
    this.newTitle = '';
    this.newMessage = '';
  }

  getCourseCode(course: string): string {
    const found = this.courses.find(c => c.id === parseInt(course));
    return found ? found.code : course;
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
