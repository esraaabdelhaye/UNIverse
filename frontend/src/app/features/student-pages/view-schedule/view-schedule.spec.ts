import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewSchedule } from './view-schedule';

describe('ViewSchedule', () => {
  let component: ViewSchedule;
  let fixture: ComponentFixture<ViewSchedule>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewSchedule]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewSchedule);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
