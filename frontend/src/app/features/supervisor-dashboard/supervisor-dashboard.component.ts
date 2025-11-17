import { Component, ChangeDetectionStrategy, signal, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule, RouterLink, RouterLinkActive } from '@angular/router';
import { SupervisorService } from '../../core/services/supervisor.service';

@Component({
  selector: 'app-supervisor-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule, RouterLink, RouterLinkActive],
  templateUrl: './supervisor-dashboard.component.html',
  styleUrls: ['./supervisor-dashboard.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SupervisorDashboardComponent implements OnInit {
  supervisorName = signal<string>('Supervisor');

  constructor(
    private router: Router,
    private supervisorService: SupervisorService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    // Get supervisor name from service
    const name = this.supervisorService.getSupervisorName();
    this.supervisorName.set(name || 'Supervisor');
    
    // Subscribe to name changes
    this.supervisorService.supervisorName$.subscribe((name: string) => {
      if (name) {
        this.supervisorName.set(name);
        this.cdr.markForCheck();
      }
    });
  }

  logout() {
    console.log("Logging out...");
    this.router.navigate(['/auth/login']);
  }
}