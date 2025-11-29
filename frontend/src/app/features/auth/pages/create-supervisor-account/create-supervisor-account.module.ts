import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CreateSupervisorAccountRoutingModule } from './create-supervisor-account-routing.module';
import { CreateSupervisorAccount } from './create-supervisor-account';

@NgModule({
  declarations: [],
  imports: [CommonModule, CreateSupervisorAccountRoutingModule, CreateSupervisorAccount],
})
export class CreateSupervisorAccountModule {}
