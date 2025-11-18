import { Component } from '@angular/core';
import { Router, Routes, RouterLink } from '@angular/router';
@Component({
  selector: 'app-choose-account',
  imports: [RouterLink],
  templateUrl: './choose-account.html',
  styleUrl: './choose-account.css',
})
export class ChooseAccount {
  constructor(private router: Router) {}

  createAccount(chosenRole: string) {
    switch (chosenRole) {
      case 'sup-prof':
        this.router.navigate(['/create-ta-account']);
        break;

      case 'ta':
        this.router.navigate(['/create-ta-account']);
        break;

      case 'student':
        this.router.navigate(['/create-student-account']);
        break;

      default:
        this.router.navigate(['/']);
    }
  }
}
