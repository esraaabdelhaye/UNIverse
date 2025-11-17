import { Component, OnInit, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; 
import { Observable } from 'rxjs';
import { Course } from '../../../../core/models/course.model';
import { SupervisorService } from '../../../../core/services/supervisor.service';

@Component({
  selector: 'app-manage-courses',
  standalone: true,
  imports: [CommonModule, FormsModule], 
  templateUrl: './manage-courses.component.html',
  styleUrls: ['./manage-courses.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ManageCoursesComponent implements OnInit {

  public courses$!: Observable<Course[]>;
  public academicLevels = [1, 2, 3, 4]; 

  constructor(
    private supervisorService: SupervisorService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.courses$ = this.supervisorService.getCourses();
  }

  toggleLevel(course: Course, level: number): void {
    const index = course.availableToLevels.indexOf(level);
    const updatedCourse: Course = { ...course };
    
    if (index > -1) {
      updatedCourse.availableToLevels = updatedCourse.availableToLevels.filter(l => l !== level);
    } else {
      updatedCourse.availableToLevels = [...updatedCourse.availableToLevels, level];
    }
    
    this.supervisorService.updateCourse(updatedCourse);
    this.cdr.markForCheck();
  }

  onToggleActive(course: Course, event: Event) {
    const isChecked = (event.target as HTMLInputElement).checked;
    const updatedCourse: Course = {
      ...course,
      isActive: isChecked
    };
    
    this.supervisorService.updateCourse(updatedCourse);
    this.cdr.markForCheck();
  }
}