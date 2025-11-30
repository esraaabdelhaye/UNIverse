import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateSupervisorAccount } from './create-supervisor-account';

describe('CreateSupervisorAccount', () => {
  let component: CreateSupervisorAccount;
  let fixture: ComponentFixture<CreateSupervisorAccount>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateSupervisorAccount]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateSupervisorAccount);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
