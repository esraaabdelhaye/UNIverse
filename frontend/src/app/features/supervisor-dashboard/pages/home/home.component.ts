import { Component, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

const MOCK_REPORTS = [
  { id: 'r1', title: 'Q3 Student Feedback Report', description: 'Overall satisfaction at 85%.' },
  { id: 'r2', title: 'Faculty Publication Rates', description: 'Average 2.3 publications/faculty.' },
];

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class HomeComponent {
  performanceReports = MOCK_REPORTS;
}