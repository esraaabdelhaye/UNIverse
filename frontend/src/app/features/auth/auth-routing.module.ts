import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ChooseAccountComponent } from './pages/choose-account/choose-account.component';
import { LoginPage } from './pages/login-page/login-page';

export const authRoutes: Routes = [
  { path: 'choose-account', component: ChooseAccountComponent },
  { path: 'login', component: LoginPage }
];

@NgModule({
  imports: [RouterModule.forChild(authRoutes)],
  exports: [RouterModule]
})
export class AuthRoutingModule {}
