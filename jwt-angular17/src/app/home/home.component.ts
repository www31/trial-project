import { Component, inject } from '@angular/core';
import { AuthService } from '../auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  authService = inject(AuthService)
  user?: any;

  constructor() {
    this.authService.getCurrentAuthUser().subscribe((r) => {
      console.log(r);
      this.user = r;
    });
  }

  logout() {
    this.authService.logout();
  }

  refreshToken() {
    this.authService.refreshToken()?.subscribe(() => {});
  }
  
}
