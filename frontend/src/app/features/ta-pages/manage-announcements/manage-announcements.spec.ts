import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageAnnouncements } from './manage-announcements';

describe('ManageAnnouncements', () => {
  let component: ManageAnnouncements;
  let fixture: ComponentFixture<ManageAnnouncements>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ManageAnnouncements]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManageAnnouncements);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
