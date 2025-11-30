import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CourseAvailability } from './course-availability';

describe('CourseAvailability', () => {
  let component: CourseAvailability;
  let fixture: ComponentFixture<CourseAvailability>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CourseAvailability]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CourseAvailability);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
