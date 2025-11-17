import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateTaAccount } from './create-ta-account';

describe('CreateTaAccount', () => {
  let component: CreateTaAccount;
  let fixture: ComponentFixture<CreateTaAccount>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateTaAccount]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateTaAccount);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
