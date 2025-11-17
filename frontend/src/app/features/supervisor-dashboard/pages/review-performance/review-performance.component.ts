import { Component, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';

const MOCK_REPORTS = [
  { id: 'r1', title: 'Q3 Student Feedback Report', description: 'Overall satisfaction at 85%.' },
  { id: 'r2', title: 'Faculty Publication Rates', description: 'Average 2.3 publications/faculty.' },
];

@Component({
  selector: 'app-review-performance',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './review-performance.component.html',
  styleUrls: ['./review-performance.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ReviewPerformanceComponent {
  performanceReports = MOCK_REPORTS;
}