import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfessorAssignment } from './professor-assignment';

describe('ProfessorAssignment', () => {
  let component: ProfessorAssignment;
  let fixture: ComponentFixture<ProfessorAssignment>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfessorAssignment]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProfessorAssignment);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
