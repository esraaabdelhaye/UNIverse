import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TaDashboard } from './ta-dashboard';

describe('TaDashboard', () => {
  let component: TaDashboard;
  let fixture: ComponentFixture<TaDashboard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TaDashboard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TaDashboard);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
