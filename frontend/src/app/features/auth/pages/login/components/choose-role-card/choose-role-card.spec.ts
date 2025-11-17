import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChooseRoleCard } from './choose-role-card';

describe('ChooseRoleCard', () => {
  let component: ChooseRoleCard;
  let fixture: ComponentFixture<ChooseRoleCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChooseRoleCard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChooseRoleCard);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
