import { Component, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-polls-voting',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './polls-voting.html',
  styleUrl: './polls-voting.css',
})
export class PollsVoting {
  private router = inject(Router);
  private authService = inject(AuthService);

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
