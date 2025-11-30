import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GradeStudents } from './grade-students';

describe('GradeStudents', () => {
  let component: GradeStudents;
  let fixture: ComponentFixture<GradeStudents>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GradeStudents]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GradeStudents);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
