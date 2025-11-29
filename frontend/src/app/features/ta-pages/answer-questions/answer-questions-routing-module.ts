import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AnswerQuestions } from './answer-questions';

const routes: Routes = [
  {
    path: '',
    component: AnswerQuestions,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AnswerQuestionsRoutingModule {}
