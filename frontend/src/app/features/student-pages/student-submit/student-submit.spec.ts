import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentSubmit } from './student-submit';

describe('StudentSubmit', () => {
  let component: StudentSubmit;
  let fixture: ComponentFixture<StudentSubmit>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StudentSubmit]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StudentSubmit);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
