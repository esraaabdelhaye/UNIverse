import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreateSupervisorAccount } from './create-supervisor-account';

const routes: Routes = [
  {
    path: '',
    component: CreateSupervisorAccount,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CreateSupervisorAccountRoutingModule {}
