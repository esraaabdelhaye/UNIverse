import { Component, OnInit, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';
import { ScheduleService, Schedule } from '../../../core/services/schedule.service';
import html2canvas from 'html2canvas';
import jsPDF from 'jspdf';

interface TimeSlot {
  time: string;
  saturday?: any[];
  sunday?: any[];
  monday?: any[];
  tuesday?: any[];
  wednesday?: any[];
  thursday?: any[];
}

@Component({
  selector: 'app-view-schedule',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, CommonModule, FormsModule],
  templateUrl: './view-schedule.html',
  styleUrl: './view-schedule.css',
})
export class ViewScheduleComponent implements OnInit {
  private router = inject(Router);
  private authService = inject(AuthService);
  private scheduleService = inject(ScheduleService);

  // User
  currentUser: any = null;

  // Form inputs
  selectedSemester: string = 'Fall';
  selectedYear: string = '2026';
  selectedAcademicYear: string = 'Year 1';

  // Data
  timetable: Schedule[] = [];
  generatedGrid: TimeSlot[] = [];

  isLoading: boolean = false;
  isGenerated: boolean = false;

  // Options
  semesters: string[] = ['Fall', 'Spring'];
  years: string[] = ['2024', '2025', '2026'];
  academicYears: string[] = ['Year 1', 'Year 2', 'Year 3', 'Year 4'];
  timeSlotsList = ['08:00', '09:00', '10:00', '11:00', '12:00', '13:00', '14:00', '15:00', '16:00'];

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
    this.fetchSchedule();
  }

  onYearChange() {
    this.fetchSchedule();
  }

  onSemesterChange() {
    this.fetchSchedule();
  }

  fetchSchedule() {
    this.isLoading = true;
    this.scheduleService.getSchedule(this.selectedSemester, this.selectedAcademicYear).subscribe({
      next: (data) => {
        this.timetable = data;
        this.mapScheduleToGrid(data);
        this.isGenerated = this.timetable.length > 0;
        this.isLoading = false;
      },
      error: (err: any) => {
        console.error('Error fetching schedule', err);
        this.isLoading = false;
      }
    });
  }

  mapScheduleToGrid(schedules: Schedule[]) {
    console.log('[GRID MAPPING] Received schedules:', schedules.length);

    const timeMap = new Map<string, any>();
    this.timeSlotsList.forEach(t => timeMap.set(t, { time: t }));

    schedules.forEach(slot => {
      let row = timeMap.get(slot.startTime);
      if (!row) {
        row = { time: slot.startTime };
        timeMap.set(slot.startTime, row);
      }

      const dayKey = slot.dayOfWeek.toLowerCase();

      const courseType = this.determineCourseType(slot.room);

      const courseData = {
        ...slot,
        courseCode: slot.course.courseCode,
        title: slot.course.name,
        instructor: (slot.course.doctors && slot.course.doctors.length > 0)
          ? slot.course.doctors.map((d: any) => d.name).join(', ')
          : 'Unassigned',
        type: courseType
      };

      if (!row[dayKey]) {
        row[dayKey] = [];
      }
      row[dayKey].push(courseData);
    });

    this.generatedGrid = Array.from(timeMap.values()).sort((a, b) => a.time.localeCompare(b.time));
    console.log('[GRID MAPPING] Generated grid rows:', this.generatedGrid.length);
  }

  determineCourseType(room: string): string {
    if (!room) return 'default';

    const roomLower = room.toLowerCase();

    if (roomLower.includes('lab')) {
      return 'lab';
    } else if (roomLower.includes('room') || roomLower.includes('hall')) {
      return 'lecture';
    } else if (roomLower.includes('section') || roomLower.includes('tutorial')) {
      return 'section';
    }

    return 'default';
  }

  downloadTimetable() {
    const timetableElement = document.querySelector('.timetable-view') as HTMLElement;

    if (!timetableElement) {
      alert('Timetable not found. Please ensure schedule is loaded.');
      return;
    }

    html2canvas(timetableElement, {
      scale: 2,
      backgroundColor: '#ffffff',
      logging: false,
      useCORS: true
    }).then(canvas => {
      const imgData = canvas.toDataURL('image/png');
      const pdf = new jsPDF('landscape', 'mm', 'a4');

      const imgWidth = 280;
      const imgHeight = (canvas.height * imgWidth) / canvas.width;

      // Add header
      pdf.setFillColor(37, 99, 235);
      pdf.rect(0, 0, pdf.internal.pageSize.getWidth(), 25, 'F');

      pdf.setTextColor(255, 255, 255);
      pdf.setFontSize(18);
      pdf.setFont('helvetica', 'bold');
      pdf.text('My Class Schedule', 15, 16);

      pdf.setFontSize(11);
      pdf.setFont('helvetica', 'normal');
      pdf.text(`${this.selectedAcademicYear} - ${this.selectedSemester}`, pdf.internal.pageSize.getWidth() - 15, 16, { align: 'right' });

      // Add schedule image
      pdf.addImage(imgData, 'PNG', 10, 30, imgWidth, imgHeight);

      // Save PDF
      pdf.save(`my-schedule-${this.selectedAcademicYear}-${this.selectedSemester}.pdf`);
    }).catch(error => {
      console.error('Error generating timetable PDF:', error);
      alert('Failed to download timetable. Please try again.');
    });
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
