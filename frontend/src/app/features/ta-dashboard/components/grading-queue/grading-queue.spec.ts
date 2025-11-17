import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GradingQueue } from './grading-queue';

describe('GradingQueue', () => {
  let component: GradingQueue;
  let fixture: ComponentFixture<GradingQueue>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GradingQueue]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GradingQueue);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
