import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubmitAssignments } from './submit-assignments';

describe('SubmitAssignments', () => {
  let component: SubmitAssignments;
  let fixture: ComponentFixture<SubmitAssignments>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SubmitAssignments]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubmitAssignments);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
