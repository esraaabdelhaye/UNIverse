import { Component } from '@angular/core';
import { ChooseAccountRoutingModule } from '../choose-account/choose-account-routing-module';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-create-ta-account',
  imports: [CommonModule],
  templateUrl: './create-ta-account.html',
  styleUrl: './create-ta-account.css',
})
export class CreateTaAccount {}
