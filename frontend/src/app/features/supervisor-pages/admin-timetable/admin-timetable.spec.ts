import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminTimetable } from './admin-timetable';

describe('AdminTimetable', () => {
  let component: AdminTimetable;
  let fixture: ComponentFixture<AdminTimetable>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminTimetable]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminTimetable);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
