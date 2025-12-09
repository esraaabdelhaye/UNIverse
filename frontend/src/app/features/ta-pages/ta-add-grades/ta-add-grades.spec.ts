import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TaAddGrades } from './ta-add-grades';

describe('TaAddGrades', () => {
  let component: TaAddGrades;
  let fixture: ComponentFixture<TaAddGrades>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TaAddGrades]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TaAddGrades);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
