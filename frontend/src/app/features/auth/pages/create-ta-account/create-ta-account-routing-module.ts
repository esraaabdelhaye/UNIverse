import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

import {CreateTaAccount} from './create-ta-account';
const routes: Routes = [
  {
    path: '',
    component: CreateTaAccount,
    children: [],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CreateTaAccountRoutingModule {}
