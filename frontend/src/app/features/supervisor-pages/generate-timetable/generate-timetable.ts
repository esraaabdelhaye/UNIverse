import { Component, OnInit, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';

interface ClassSlot {
  courseCode: string;
  title: string;
  instructor: string;
  room: string;
  type: 'primary' | 'accent' | 'conflict';
}

interface TimeSlot {
  time: string;
  monday?: ClassSlot;
  tuesday?: ClassSlot;
  wednesday?: ClassSlot;
  thursday?: ClassSlot;
  friday?: ClassSlot;
}

interface Constraint {
  avoidBackToBack: boolean;
  prioritizeMorning: boolean;
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

  // Form inputs
  selectedSemester: string = 'Fall 2024';
  coursesToInclude: string = '';
  instructorAvailability: string = '';
  roomCapacity: string = '';

  // Constraints
  constraints: Constraint = {
    avoidBackToBack: true,
    prioritizeMorning: false,
  };

  // Timetable data
  timetable: TimeSlot[] = [
    {
      time: '09:00 - 10:00',
      tuesday: {
        courseCode: 'MATH201',
        title: 'MATH201',
        instructor: 'Dr. Abel',
        room: 'Room 101',
        type: 'primary',
      },
      thursday: {
        courseCode: 'MATH201',
        title: 'MATH201',
        instructor: 'Dr. Abel',
        room: 'Room 101',
        type: 'primary',
      },
    },
    {
      time: '10:00 - 11:00',
      monday: {
        courseCode: 'CS101',
        title: 'CS101',
        instructor: 'Dr. Evans',
        room: 'Hall A',
        type: 'conflict',
      },
      wednesday: {
        courseCode: 'CS101',
        title: 'CS101',
        instructor: 'Dr. Evans',
        room: 'Hall A',
        type: 'primary',
      },
      friday: {
        courseCode: 'CS101',
        title: 'CS101',
        instructor: 'Dr. Evans',
        room: 'Hall A',
        type: 'primary',
      },
    },
    {
      time: '11:00 - 12:00',
      monday: {
        courseCode: 'ENG203',
        title: 'ENG203',
        instructor: 'Dr. Evans',
        room: 'Room 204',
        type: 'conflict',
      },
      tuesday: {
        courseCode: 'PHY310 Lab',
        title: 'PHY310 Lab',
        instructor: 'Dr. Curie',
        room: 'Lab 3',
        type: 'accent',
      },
      thursday: {
        courseCode: 'PHY310 Lab',
        title: 'PHY310 Lab',
        instructor: 'Dr. Curie',
        room: 'Lab 3',
        type: 'accent',
      },
    },
    {
      time: '12:00 - 13:00',
    },
    {
      time: '13:00 - 14:00',
      monday: {
        courseCode: 'HIS450',
        title: 'HIS450',
        instructor: 'Dr. Smith',
        room: 'Room 204',
        type: 'primary',
      },
      wednesday: {
        courseCode: 'HIS450',
        title: 'HIS450',
        instructor: 'Dr. Smith',
        room: 'Room 204',
        type: 'primary',
      },
    },
  ];

  semesters: string[] = ['Fall 2024', 'Spring 2025'];
  conflictCount: number = 2;
  isGenerated: boolean = true;

  ngOnInit(): void {
    this.initializeTimetable();
  }

  initializeTimetable(): void {
    console.log('Timetable initialized');
  }

  generateTimetable(): void {
    console.log('Generating timetable with constraints:', this.constraints);
    console.log('Courses to include:', this.coursesToInclude);
    console.log('Instructor availability:', this.instructorAvailability);
    console.log('Room capacity:', this.roomCapacity);

    // Simulate timetable generation
    this.isGenerated = true;
    this.conflictCount = Math.floor(Math.random() * 3);
    alert(`Timetable generated with ${this.conflictCount} conflict(s)`);
  }

  reviewConflicts(): void {
    console.log('Reviewing conflicts. Total conflicts:', this.conflictCount);
    alert(`Found ${this.conflictCount} scheduling conflict(s).\n\nReview and resolve them to publish.`);
  }

  manuallyAdjust(): void {
    console.log('Opening manual adjustment mode');
    alert('Manual adjustment mode enabled. Click on any time slot to edit.');
  }

  publishTimetable(): void {
    if (this.conflictCount > 0) {
      alert(`Cannot publish: ${this.conflictCount} conflict(s) remain. Resolve them first.`);
      return;
    }

    if (
      confirm(
        'Are you sure you want to publish this timetable? This action cannot be undone.'
      )
    ) {
      console.log('Publishing timetable');
      alert('Timetable published successfully! All users have been notified.');
    }
  }

  resolveConflict(slotIndex: number, dayKey: string): void {
    console.log(`Resolving conflict at ${slotIndex} on ${dayKey}`);
    this.conflictCount = Math.max(0, this.conflictCount - 1);
    alert('Conflict resolved! Timetable updated.');
  }

  getClassSlot(dayOfWeek: string, timeSlot: TimeSlot): ClassSlot | undefined {
    const day = dayOfWeek.toLowerCase();
    const key = day as keyof TimeSlot;
    return timeSlot[key] as ClassSlot | undefined;
  }

  isLunchBreak(time: string): boolean {
    return time === '12:00 - 13:00';
  }

  updateConstraint(constraintName: keyof Constraint, value: boolean): void {
    this.constraints[constraintName] = value;
    console.log(`Updated constraint ${constraintName}: ${value}`);
  }

  downloadTimetable(): void {
    console.log('Downloading timetable...');
    let timetableContent = `TIMETABLE - ${this.selectedSemester}\n`;
    timetableContent += '='.repeat(70) + '\n\n';

    this.timetable.forEach(slot => {
      timetableContent += `Time: ${slot.time}\n`;
      if (slot.monday) timetableContent += `  Monday: ${slot.monday.courseCode}\n`;
      if (slot.tuesday) timetableContent += `  Tuesday: ${slot.tuesday.courseCode}\n`;
      if (slot.wednesday) timetableContent += `  Wednesday: ${slot.wednesday.courseCode}\n`;
      if (slot.thursday) timetableContent += `  Thursday: ${slot.thursday.courseCode}\n`;
      if (slot.friday) timetableContent += `  Friday: ${slot.friday.courseCode}\n`;
      timetableContent += '\n';
    });

    const element = document.createElement('a');
    element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(timetableContent));
    element.setAttribute('download', `timetable-${this.selectedSemester}.txt`);
    element.style.display = 'none';
    document.body.appendChild(element);
    element.click();
    document.body.removeChild(element);
    alert('Timetable downloaded successfully!');
  }

  exportAsCalendar(): void {
    console.log('Exporting as calendar...');
    alert('Timetable exported as calendar file (ICS format)');
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
