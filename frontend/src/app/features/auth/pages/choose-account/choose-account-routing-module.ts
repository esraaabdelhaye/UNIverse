import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChooseAccount } from './choose-account';

const routes: Routes = [
  { path: '', component: ChooseAccount }  // just the sign-up page
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ChooseAccountRoutingModule {}
