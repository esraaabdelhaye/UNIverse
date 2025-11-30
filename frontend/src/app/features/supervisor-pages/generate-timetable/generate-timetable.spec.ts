import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GenerateTimetable } from './generate-timetable';

describe('GenerateTimetable', () => {
  let component: GenerateTimetable;
  let fixture: ComponentFixture<GenerateTimetable>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GenerateTimetable]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GenerateTimetable);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
