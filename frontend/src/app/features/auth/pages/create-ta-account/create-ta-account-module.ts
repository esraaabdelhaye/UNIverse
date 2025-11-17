import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CreateTaAccount } from './create-ta-account';
import { CreateTaAccountRoutingModule } from './create-ta-account-routing-module';

@NgModule({
  declarations: [],
  imports: [CommonModule, CreateTaAccount, CreateTaAccountRoutingModule],
})
export class CreateTaAccountModule {}
