import { Component, OnInit } from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {MatIconModule} from '@angular/material/icon';

interface TimeSlot {
  time: string;
  courses: { course: string; instructor: string; room: string; dept: string }[];
}

@Component({
  selector: 'app-admin-timetable',
  standalone: true,
  imports: [CommonModule, FormsModule, MatIconModule],
  templateUrl: './admin-timetable.html',
  styleUrls: ['./admin-timetable.css']
})
export class AdminTimetableComponent implements OnInit {
  selectedSemester: string = 'Fall 2024';
  selectedWeek: number = 1;
  currentDate: Date = new Date();

  semesters = ['Fall 2024', 'Spring 2025', 'Summer 2025'];
  daysOfWeek = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday'];
  timeSlots = ['09:00', '10:00', '11:00', '12:00', '13:00', '14:00', '15:00', '16:00'];

  timetable: { [day: string]: { [time: string]: { course: string; instructor: string; room: string; dept: string }[] } } = {
    'Monday': {
      '09:00': [{ course: 'CS101', instructor: 'Dr. Alice', room: 'Hall A', dept: 'CS' }],
      '10:00': [{ course: 'MATH201', instructor: 'Prof. Newton', room: 'Room 204', dept: 'MATH' }],
      '13:00': [{ course: 'ENG201', instructor: 'Prof. Lee', room: 'Hall B', dept: 'ENG' }],
    },
    'Tuesday': {
      '10:00': [{ course: 'CS201', instructor: 'Dr. Knuth', room: 'Lab 1', dept: 'CS' }],
      '14:00': [{ course: 'PHYS150', instructor: 'Dr. Einstein', room: 'Lab 3', dept: 'PHYS' }],
    },
    'Wednesday': {
      '11:00': [{ course: 'CHEM101', instructor: 'Prof. Curie', room: 'Lab 2', dept: 'CHEM' }],
      '15:00': [{ course: 'HIST212', instructor: 'Dr. Smith', room: 'Room 301', dept: 'HIST' }],
    },
    'Thursday': {
      '09:00': [{ course: 'CS101', instructor: 'Dr. Alice', room: 'Hall A', dept: 'CS' }],
      '13:00': [{ course: 'MATH201', instructor: 'Prof. Newton', room: 'Room 204', dept: 'MATH' }],
    },
    'Friday': {
      '10:00': [{ course: 'CS301', instructor: 'Dr. Downy', room: 'Lab 1', dept: 'CS' }],
      '16:00': [{ course: 'ENG201', instructor: 'Prof. Lee', room: 'Hall B', dept: 'ENG' }],
    }
  };

  deptColors: { [key: string]: string } = {
    'CS': '#42A5F5',
    'MATH': '#66BB6A',
    'PHYS': '#FF9800',
    'CHEM': '#F44336',
    'ENG': '#9C27B0',
    'HIST': '#00BCD4'
  };

  ngOnInit() {}

  getCourses(day: string, time: string): any[] {
    return this.timetable[day]?.[time] || [];
  }

  getDeptColor(dept: string): string {
    return this.deptColors[dept] || '#26A69A';
  }

  previousWeek() {
    this.selectedWeek = Math.max(1, this.selectedWeek - 1);
  }

  nextWeek() {
    this.selectedWeek = Math.min(52, this.selectedWeek + 1);
  }

  goToToday() {
    this.selectedWeek = 1;
  }
}
