import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageStudentGrps } from './manage-student-grps';

describe('ManageStudentGrps', () => {
  let component: ManageStudentGrps;
  let fixture: ComponentFixture<ManageStudentGrps>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ManageStudentGrps]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManageStudentGrps);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
