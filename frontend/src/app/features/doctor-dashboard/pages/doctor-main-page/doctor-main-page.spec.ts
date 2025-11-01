import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DoctorMainPage } from './doctor-main-page';

describe('DoctorMainPage', () => {
  let component: DoctorMainPage;
  let fixture: ComponentFixture<DoctorMainPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DoctorMainPage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DoctorMainPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
