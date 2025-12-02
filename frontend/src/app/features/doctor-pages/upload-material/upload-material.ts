import { Component, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-upload-material',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './upload-material.html',
  styleUrl: './upload-material.css',
})
export class UploadMaterial {
  private router = inject(Router);
  private authService = inject(AuthService);

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
