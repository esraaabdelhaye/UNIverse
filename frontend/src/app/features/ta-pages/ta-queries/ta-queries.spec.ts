import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TaQueries } from './ta-queries';

describe('TaQueries', () => {
  let component: TaQueries;
  let fixture: ComponentFixture<TaQueries>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TaQueries]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TaQueries);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
