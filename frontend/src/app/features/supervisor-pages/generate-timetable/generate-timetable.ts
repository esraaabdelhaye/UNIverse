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
    // Initialize timetable with default values
    console.log('Timetable initialized');
  }

  generateTimetable(): void {
    console.log('Generating timetable with constraints:', this.constraints);
    console.log('Courses to include:', this.coursesToInclude);
    console.log('Instructor availability:', this.instructorAvailability);
    console.log('Room capacity:', this.roomCapacity);

    // Simulate timetable generation
    this.isGenerated = true;
    this.conflictCount = 2;

    // Here you would call a service to generate the timetable
    // this.timetableService.generateTimetable(constraints).subscribe(...)
  }

  reviewConflicts(): void {
    console.log('Reviewing conflicts. Total conflicts:', this.conflictCount);
    // Implement conflict review logic
  }

  manuallyAdjust(): void {
    console.log('Opening manual adjustment mode');
    // Implement manual adjustment logic
  }

  publishTimetable(): void {
    if (
      confirm(
        'Are you sure you want to publish this timetable? This action cannot be undone.'
      )
    ) {
      console.log('Publishing timetable');
      // Implement publish logic
    }
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

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
