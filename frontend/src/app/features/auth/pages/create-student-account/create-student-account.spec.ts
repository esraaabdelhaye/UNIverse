import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateStudentAccount } from './create-student-account';

describe('CreateStudentAccount', () => {
  let component: CreateStudentAccount;
  let fixture: ComponentFixture<CreateStudentAccount>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateStudentAccount]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateStudentAccount);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
