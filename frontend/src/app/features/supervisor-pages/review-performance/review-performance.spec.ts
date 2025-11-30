import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewPerformance } from './review-performance';

describe('ReviewPerformance', () => {
  let component: ReviewPerformance;
  let fixture: ComponentFixture<ReviewPerformance>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReviewPerformance]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReviewPerformance);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
