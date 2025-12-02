import { Component, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-answer-questions',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './answer-questions.html',
  styleUrl: './answer-questions.css',
})
export class AnswerQuestions {
  private router = inject(Router);
  private authService = inject(AuthService);

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
