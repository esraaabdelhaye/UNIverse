import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-choose-role-card',
  imports: [],
  templateUrl: './choose-role-card.html',
  styleUrl: './choose-role-card.css',
})
export class ChooseRoleCard {
  @Input() id!: string;
  @Input() mainText!: string;
  @Input() subText!: string;
}
