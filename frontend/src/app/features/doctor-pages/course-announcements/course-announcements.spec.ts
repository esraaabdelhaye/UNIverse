import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CourseAnnouncements } from './course-announcements';

describe('CourseAnnouncements', () => {
  let component: CourseAnnouncements;
  let fixture: ComponentFixture<CourseAnnouncements>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CourseAnnouncements]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CourseAnnouncements);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
