import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-choose-account',
  standalone: true,
  imports: [],
  templateUrl: './choose-account.html',
  styleUrl: './choose-account.css',
})
export class ChooseAccount {
  constructor(private router: Router) {}

  selectRole(role: 'supervisor' | 'ta' | 'student') {
    switch (role) {
      case 'supervisor':
        this.router.navigate(['/create-supervisor-account']);
        break;
      case 'ta':
        this.router.navigate(['/create-ta-account']);
        break;
      case 'student':
        this.router.navigate(['/create-student-account']);
        break;
    }
  }
}
