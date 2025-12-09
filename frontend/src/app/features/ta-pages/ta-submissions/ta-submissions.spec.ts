import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TaSubmissions } from './ta-submissions';

describe('TaSubmissions', () => {
  let component: TaSubmissions;
  let fixture: ComponentFixture<TaSubmissions>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TaSubmissions]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TaSubmissions);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
