import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewGrades } from './view-grades';

describe('ViewGrades', () => {
  let component: ViewGrades;
  let fixture: ComponentFixture<ViewGrades>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewGrades]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewGrades);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
