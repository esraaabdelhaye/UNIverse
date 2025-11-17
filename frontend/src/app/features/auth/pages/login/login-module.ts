import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Login } from './login';
import { LoginRoutingModule } from './login-routing-module';

@NgModule({
  declarations: [],
  imports: [CommonModule, Login, LoginRoutingModule],
})
export class LoginModule {}
