import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AnswerQuestionsRoutingModule } from './answer-questions-routing-module';
import { AnswerQuestions } from './answer-questions';

@NgModule({
  declarations: [],
  imports: [CommonModule, AnswerQuestionsRoutingModule, AnswerQuestions],
})
export class AnswerQuestionsModule {}
