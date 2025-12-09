import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

interface Event {
  id: number;
  title: string;
  time: string;
  category: string;
  color: string;
}

interface CalendarDay {
  date: number;
  isCurrentMonth: boolean;
  isToday: boolean;
  events: Event[];
}

interface CalendarWeek {
  days: CalendarDay[];
}

@Component({
  selector: 'app-view-schedule',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './view-schedule.html',
  styleUrl: './view-schedule.css',
})
export class ViewSchedule implements OnInit {
  private router = inject(Router);
  private authService = inject(AuthService);

  events: Event[] = [];
  calendarWeeks: CalendarWeek[] = [];
  currentMonth = 'October 2024';
  currentMonthDate = new Date(2024, 9, 1);
  viewType = 'month';

  filters = {
    classes: true,
    deadlines: true,
    universityEvents: true,
    personal: false,
  };

  ngOnInit() {
    this.loadEvents();
    this.generateCalendar();
  }

  loadEvents() {
    this.events = [
      {
        id: 1,
        title: 'CS101 Lecture',
        time: 'Today, 10:00 AM - 11:00 AM',
        category: 'classes',
        color: 'event-blue',
      },
      {
        id: 2,
        title: 'CS202 Assignment Due',
        time: 'Tomorrow, 11:59 PM',
        category: 'deadlines',
        color: 'event-red',
      },
      {
        id: 3,
        title: 'Study Group: DS310',
        time: 'Oct 25, 3:00 PM',
        category: 'personal',
        color: 'event-green',
      },
      {
        id: 4,
        title: 'University Town Hall',
        time: 'Oct 28, 6:00 PM',
        category: 'universityEvents',
        color: 'event-purple',
      },
      {
        id: 5,
        title: 'DS310 Lecture',
        time: 'Oct 29, 1:00 PM - 2:30 PM',
        category: 'classes',
        color: 'event-blue',
      },
    ];
  }

  generateCalendar() {
    const year = this.currentMonthDate.getFullYear();
    const month = this.currentMonthDate.getMonth();

    const firstDay = new Date(year, month, 1);
    const lastDay = new Date(year, month + 1, 0);
    const prevLastDay = new Date(year, month, 0);

    const firstDayOfWeek = firstDay.getDay();
    const lastDateOfMonth = lastDay.getDate();
    const prevLastDate = prevLastDay.getDate();

    let days: CalendarDay[] = [];

    // Previous month days
    for (let i = firstDayOfWeek - 1; i >= 0; i--) {
      days.push({
        date: prevLastDate - i,
        isCurrentMonth: false,
        isToday: false,
        events: [],
      });
    }

    // Current month days
    for (let i = 1; i <= lastDateOfMonth; i++) {
      const isToday = new Date().getDate() === i;
      const dayEvents = this.getEventsForDay(i);
      days.push({
        date: i,
        isCurrentMonth: true,
        isToday,
        events: dayEvents,
      });
    }

    // Next month days
    const remainingDays = 42 - days.length;
    for (let i = 1; i <= remainingDays; i++) {
      days.push({
        date: i,
        isCurrentMonth: false,
        isToday: false,
        events: [],
      });
    }

    // Create weeks
    this.calendarWeeks = [];
    for (let i = 0; i < days.length; i += 7) {
      this.calendarWeeks.push({
        days: days.slice(i, i + 7),
      });
    }
  }

  getEventsForDay(day: number): Event[] {
    const dayEventMap: { [key: number]: Event[] } = {
      8: [this.events[0]],
      11: [this.events[0]],
      14: [this.events[1]],
      22: [this.events[1]],
      23: [this.events[0]],
      25: [this.events[2]],
      28: [this.events[3]],
      29: [this.events[4]],
    };
    return dayEventMap[day] || [];
  }

  previousMonth() {
    this.currentMonthDate.setMonth(this.currentMonthDate.getMonth() - 1);
    this.updateMonthDisplay();
    this.generateCalendar();
  }

  nextMonth() {
    this.currentMonthDate.setMonth(this.currentMonthDate.getMonth() + 1);
    this.updateMonthDisplay();
    this.generateCalendar();
  }

  updateMonthDisplay() {
    const options = { year: 'numeric', month: 'long' } as const;
    this.currentMonth = this.currentMonthDate.toLocaleDateString(
      'en-US',
      options
    );
  }

  setViewType(type: string) {
    this.viewType = type;
  }

  toggleFilter(filterName: keyof typeof this.filters) {
    this.filters[filterName] = !this.filters[filterName];
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
