// Imports remain checking needed
import { Component, OnInit, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';
import { ScheduleService, Schedule, TimetableRequest } from '../../../core/services/schedule.service';
import { CourseService } from '../../../core/services/course.service';
import html2canvas from 'html2canvas';

interface TimeSlot {
  time: string;
  saturday?: any;
  sunday?: any;
  monday?: any;
  tuesday?: any;
  wednesday?: any;
  thursday?: any;
}

@Component({
  selector: 'app-generate-timetable',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, CommonModule, FormsModule],
  templateUrl: './generate-timetable.html',
  styleUrl: './generate-timetable.css',
})
export class GenerateTimetable implements OnInit {
  private router = inject(Router);
  private authService = inject(AuthService);
  private scheduleService = inject(ScheduleService);
  private courseService = inject(CourseService);

  // Form inputs
  selectedSemester: string = 'Fall';
  selectedYear: string = '2024';
  selectedAcademicYear: string = 'Year 1';

  // Data
  availableCourses: any[] = [];

  // Constraints
  constraints: any[] = []; // { courseId, code, title, room }

  // Output
  timetable: Schedule[] = [];
  generatedGrid: TimeSlot[] = [];

  isLoading: boolean = false;
  isGenerated: boolean = false;
  errorMessage: string = '';

  // Options
  semesters: string[] = ['Fall', 'Spring'];
  years: string[] = ['2024', '2025', '2026'];
  academicYears: string[] = ['Year 1', 'Year 2', 'Year 3', 'Year 4'];
  timeSlotsList = ['09:00', '10:00', '11:00', '12:00', '13:00']; // Limited to 5 slots (max capacity per day)

  // New Constraint Form
  newConstraint = {
    courseId: null,
    room: ''
  };

  // Edit Modal
  isEditModalOpen: boolean = false;
  selectedSlot: any = null; // The slot being edited
  editForm = {
    startTime: '',
    room: '',
    dayOfWeek: ''
  };


  ngOnInit(): void {
    this.fetchCourses();
    this.fetchSchedule();
  }

  fetchCourses() {
    this.courseService.getAllCourses(0, 100).subscribe({
      next: (res: any) => {
        let list = [];
        if (res.data && res.data.content) {
          list = res.data.content;
        } else if (Array.isArray(res.data)) {
          list = res.data;
        }

        // Map DTO to component expected format
        this.availableCourses = list.map((c: any) => ({
          ...c,
          id: c.id,
          name: c.courseTitle || c.name, // Ensure name is populated for dropdown display
          code: c.courseCode
        }));
      },
      error: (err: any) => console.error("Failed to fetch courses", err)
    });
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

  // Constraint Management
  addConstraint() {
    if (!this.newConstraint.courseId) return;

    const course = this.availableCourses.find(c => c.id == this.newConstraint.courseId);
    if (!course) return;

    // Prevent duplicate
    if (this.constraints.find(c => c.courseId == course.id)) {
      alert("Constraint for this course already exists.");
      return;
    }

    this.constraints.push({
      courseId: course.id,
      code: course.courseCode,
      title: course.name,
      room: this.newConstraint.room || 'Any'
    });

    // Reset selection
    this.newConstraint.courseId = null;
    this.newConstraint.room = '';
  }

  removeConstraint(c: any) {
    this.constraints = this.constraints.filter(x => x.courseId !== c.courseId);
  }

  generateTimetable(): void {
    if (!confirm('This will overwrite existing schedule for this Year. Continue?')) return;

    this.isLoading = true;

    // Collect all course IDs from constraints + any unconstrained ones?
    // User wants "Add Course" logic. So we should ideally schedule ALL valid courses for this semester/year structure?
    // Or ONLY what user puts in the list?
    // Reverting to "List based": typically implies we build the list of courses to schedule.
    // So we will use the courses in 'constraints' list as the scope. 
    // If room is 'Any', we send just ID.

    // NOTE: Backend needs list of IDs.
    const courseIds = this.constraints.map(c => c.courseId);
    if (courseIds.length === 0) {
      alert("Please add at least one course to the list.");
      this.isLoading = false;
      return;
    }

    const fixedRoomsMap: any = {};
    this.constraints.forEach(c => {
      if (c.room && c.room !== 'Any') {
        fixedRoomsMap[c.courseId] = c.room;
      }
    });

    const request: any = {
      courseIds: courseIds,
      rooms: ['Room 101', 'Room 102', 'Lab 1', 'Hall A'],
      days: ['Saturday', 'Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday'],
      timeSlots: this.timeSlotsList,
      semester: this.selectedSemester,
      academicYear: this.selectedAcademicYear,
      fixedRooms: fixedRoomsMap
    };

    this.scheduleService.generateTimetable(request).subscribe({
      next: (data) => {
        this.timetable = data;
        this.mapScheduleToGrid(data);
        this.isGenerated = true;
        this.isLoading = false;
        alert('Timetable Generated Successfully!');
      },
      error: (err: any) => {
        this.isLoading = false;
        alert('Generation Failed: ' + (err.error?.message || 'Unknown Error'));
      }
    });
  }

  mapScheduleToGrid(schedules: Schedule[]) {
    console.log('[GRID MAPPING] Received schedules:', schedules.length);
    schedules.forEach((s, idx) => {
      console.log(`  [${idx}] Course: ${s.course?.courseCode}, Day: ${s.dayOfWeek}, Time: ${s.startTime}, Room: ${s.room}`);
    });

    const timeMap = new Map<string, any>();
    this.timeSlotsList.forEach(t => timeMap.set(t, { time: t }));

    schedules.forEach(slot => {
      let row = timeMap.get(slot.startTime);
      if (!row) {
        row = { time: slot.startTime };
        timeMap.set(slot.startTime, row);
      }

      const dayKey = slot.dayOfWeek.toLowerCase();
      console.log(`  Mapping ${slot.course?.courseCode} to row[${dayKey}] at time ${slot.startTime}`);

      const courseData = {
        ...slot, // Keep full object for editing
        courseCode: slot.course.courseCode,
        title: slot.course.name,
        instructor: (slot.course.doctors && slot.course.doctors.length > 0) ? slot.course.doctors.map((d: any) => d.name).join(', ') : 'Unassigned',
        type: 'primary'
      };

      // Store as array to support multiple courses at same time
      if (!row[dayKey]) {
        row[dayKey] = [];
      }
      row[dayKey].push(courseData);
    });

    this.generatedGrid = Array.from(timeMap.values()).sort((a, b) => a.time.localeCompare(b.time));
    console.log('[GRID MAPPING] Generated grid rows:', this.generatedGrid.length);
  }

  // Interactive Grid
  onCellClick(cellData: any) {
    if (!cellData) return;
    this.selectedSlot = cellData;
    this.editForm = {
      startTime: cellData.startTime,
      room: cellData.room,
      dayOfWeek: cellData.dayOfWeek
    };
    this.isEditModalOpen = true;
  }

  closeEditModal() {
    this.isEditModalOpen = false;
    this.selectedSlot = null;
  }

  updateSlot() {
    if (!this.selectedSlot) return;

    const updated = {
      ...this.selectedSlot,
      startTime: this.editForm.startTime,
      // Auto calc end time logic should be in backend or service, but we pass full obj
      endTime: this.calculateEndTime(this.editForm.startTime),
      room: this.editForm.room,
      dayOfWeek: this.editForm.dayOfWeek
    };

    this.scheduleService.updateManualSlot(this.selectedSlot.id, updated).subscribe({
      next: () => {
        alert("Slot updated!");
        this.closeEditModal();
        this.fetchSchedule();
      },
      error: (err: any) => alert("Update failed: " + (err.error?.message || "Conflict or Error"))
    });
  }

  deleteSlot() {
    if (!this.selectedSlot || !confirm("Delete this slot?")) return;

    this.scheduleService.deleteSlot(this.selectedSlot.id).subscribe({
      next: () => {
        this.closeEditModal();
        this.fetchSchedule();
      },
      error: (err: any) => alert("Delete failed")
    });
  }

  calculateEndTime(startTime: string): string {
    try {
      const parts = startTime.split(':');
      const hour = parseInt(parts[0]) + 1;
      return (hour < 10 ? '0' : '') + hour + ':' + parts[1];
    } catch { return startTime; }
  }

  downloadTimetable() {
    const timetableElement = document.querySelector('.timetable-view') as HTMLElement;

    if (!timetableElement) {
      alert('Timetable not found. Please generate a timetable first.');
      return;
    }

    // Capture the timetable as image
    html2canvas(timetableElement, {
      scale: 2,  // Higher quality
      backgroundColor: '#ffffff',
      logging: false,
      useCORS: true
    }).then(canvas => {
      // Convert to image and download
      const link = document.createElement('a');
      link.download = `timetable-${this.selectedSemester.replace(/\s+/g, '-')}.png`;
      link.href = canvas.toDataURL('image/png');
      link.click();
    }).catch(error => {
      console.error('Error generating timetable image:', error);
      alert('Failed to download timetable. Please try again.');
    });
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
