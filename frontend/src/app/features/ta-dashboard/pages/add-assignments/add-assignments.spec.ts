import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddAssignments } from './add-assignments';

describe('AddAssignments', () => {
  let component: AddAssignments;
  let fixture: ComponentFixture<AddAssignments>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddAssignments]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddAssignments);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
