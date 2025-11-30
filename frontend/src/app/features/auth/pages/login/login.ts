import { Component } from '@angular/core';
import { Router, Routes, RouterLink } from '@angular/router';
import { ChooseAccount } from '../choose-account/choose-account';
import { DoctorDashboard } from '../../../doctor-pages/doctor-dashboard/doctor-dashboard';
@Component({
  selector: 'app-login',
  standalone: true,
  imports: [RouterLink],
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
        this.router.navigate(['/ta-dashboard']);
        break;
      case 'student':
        this.router.navigate(['/student-dashboard']);
        break;
    }
  }

  SignUpClicked() {
    this.router.navigate(['choose-account']);
  }
}
