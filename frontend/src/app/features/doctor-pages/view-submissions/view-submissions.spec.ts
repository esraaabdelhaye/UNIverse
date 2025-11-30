import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewSubmissions } from './view-submissions';

describe('ViewSubmissions', () => {
  let component: ViewSubmissions;
  let fixture: ComponentFixture<ViewSubmissions>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewSubmissions]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewSubmissions);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
