import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminFaculty } from './admin-faculty';

describe('AdminFaculty', () => {
  let component: AdminFaculty;
  let fixture: ComponentFixture<AdminFaculty>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminFaculty]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminFaculty);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
