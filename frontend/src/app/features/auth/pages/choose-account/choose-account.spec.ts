import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChooseAccount } from './choose-account';

describe('ChooseAccount', () => {
  let component: ChooseAccount;
  let fixture: ComponentFixture<ChooseAccount>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChooseAccount]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChooseAccount);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
