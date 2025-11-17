import { Component, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';

const MOCK_CONFLICTS = [
  { id: 't1', type: 'error', message: 'CS101 & ENG203 clash on Mon 10 AM. Dr. Evans double-booked.' },
  { id: 't2', type: 'warning', message: 'Room 301 is over capacity for MATH201.' },
];

@Component({
  selector: 'app-gen-timetable',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './gen-timetable.component.html',
  styleUrls: ['./gen-timetable.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class GenTimetableComponent {
  conflicts = MOCK_CONFLICTS;
}