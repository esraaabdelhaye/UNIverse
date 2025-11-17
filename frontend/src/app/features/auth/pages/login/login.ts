import { Component } from '@angular/core';
import { Router, Routes } from '@angular/router';
import { ChooseAccount } from '../choose-account/choose-account';
import { DoctorDashboard } from '../../../doctor-dashboard/doctor-dashboard';
@Component({
  selector: 'app-login',
  imports: [],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  selectedRole = 'supervisor-prof'; // holds the chosen value

  constructor(private router: Router) {}
  selectRole(role: string) {
    this.selectedRole = role;
  }
  LoginBtnClicked() {
    console.log(this.selectedRole);
    switch (this.selectedRole) {
      case 'supervisor-prof':
        this.router.navigate(['/doctor-dashboard']);
        break;
      case 'ta':
        this.router.navigate(['/doctor-dashboard']);
        break;
      case 'student':
        this.router.navigate(['/student-dashboard']);
        break;
    }
  }
}
