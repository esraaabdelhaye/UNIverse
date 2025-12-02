import { Component, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-upload-materials',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './upload-materials.html',
  styleUrl: './upload-materials.css',
})
export class UploadMaterials {
  private router = inject(Router);
  private authService = inject(AuthService);

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
