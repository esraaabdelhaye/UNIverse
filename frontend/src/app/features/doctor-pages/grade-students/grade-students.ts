import { Component, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-grade-students',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './grade-students.html',
  styleUrl: './grade-students.css',
})
export class GradeStudents {
  private router = inject(Router);
  private authService = inject(AuthService);

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
