import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentQueries } from './student-queries';

describe('StudentQueries', () => {
  let component: StudentQueries;
  let fixture: ComponentFixture<StudentQueries>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StudentQueries]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StudentQueries);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
