import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

import { ChooseAccount } from './choose-account';

const routes: Routes = [
  {
    path: '',
    component: ChooseAccount,
    children: [],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ChooseAccountRoutingModule {}
