import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddGrades } from './add-grades';

describe('AddGrades', () => {
  let component: AddGrades;
  let fixture: ComponentFixture<AddGrades>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddGrades]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddGrades);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
