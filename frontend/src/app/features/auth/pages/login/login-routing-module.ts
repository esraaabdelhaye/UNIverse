import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { Login } from './login';

const routes: Routes = [
  {
    path: '',
    component: Login
  },
  {
    path: 'choose-account',
    loadChildren: () =>
      import('./../choose-account/choose-account-routing-module')
        .then(m => m.ChooseAccountRoutingModule)
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LoginRoutingModule {}
