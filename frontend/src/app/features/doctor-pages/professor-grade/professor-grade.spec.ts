import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfessorGrade } from './professor-grade';

describe('ProfessorGrade', () => {
  let component: ProfessorGrade;
  let fixture: ComponentFixture<ProfessorGrade>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfessorGrade]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProfessorGrade);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
