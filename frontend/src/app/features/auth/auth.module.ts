import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { authRoutes } from './auth-routing.module';

import { ChooseAccountComponent } from './pages/choose-account/choose-account.component';
import { LoginPage } from './pages/login-page/login-page';

@NgModule({
  declarations: [
    ChooseAccountComponent,
    LoginPage
  ],
  imports: [
    RouterModule.forChild(authRoutes)
  ]
})
export class AuthModule {}
