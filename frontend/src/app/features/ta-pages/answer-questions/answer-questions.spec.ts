import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnswerQuestions } from './answer-questions';

describe('AnswerQuestions', () => {
  let component: AnswerQuestions;
  let fixture: ComponentFixture<AnswerQuestions>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnswerQuestions]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnswerQuestions);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
