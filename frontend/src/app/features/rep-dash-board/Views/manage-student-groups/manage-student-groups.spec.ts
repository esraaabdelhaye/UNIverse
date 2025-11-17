import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageStudentGroups } from './manage-student-groups';

describe('ManageStudentGroups', () => {
  let component: ManageStudentGroups;
  let fixture: ComponentFixture<ManageStudentGroups>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ManageStudentGroups]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManageStudentGroups);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
