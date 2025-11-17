import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RepDashBoard } from './rep-dash-board';

describe('RepDashBoard', () => {
  let component: RepDashBoard;
  let fixture: ComponentFixture<RepDashBoard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RepDashBoard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RepDashBoard);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
