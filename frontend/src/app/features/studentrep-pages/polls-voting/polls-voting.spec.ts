import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PollsVoting } from './polls-voting';

describe('PollsVoting', () => {
  let component: PollsVoting;
  let fixture: ComponentFixture<PollsVoting>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PollsVoting]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PollsVoting);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
