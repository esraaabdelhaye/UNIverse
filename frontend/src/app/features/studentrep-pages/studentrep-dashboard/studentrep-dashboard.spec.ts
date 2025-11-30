import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentrepDashboard } from './studentrep-dashboard';

describe('StudentrepDashboard', () => {
  let component: StudentrepDashboard;
  let fixture: ComponentFixture<StudentrepDashboard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StudentrepDashboard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StudentrepDashboard);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
