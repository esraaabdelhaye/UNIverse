import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfessorCourses } from './professor-courses';

describe('ProfessorCourses', () => {
  let component: ProfessorCourses;
  let fixture: ComponentFixture<ProfessorCourses>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfessorCourses]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProfessorCourses);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
