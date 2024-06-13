import { Component, inject } from '@angular/core';
import { LoginService } from '../login.services';

@Component({
  selector: 'app-page-not-found',
  standalone: true,
  imports: [],
  templateUrl: './page-not-found.component.html',
  styleUrl: './page-not-found.component.css'
})
export class PageNotFoundComponent {
  loginService = inject(LoginService)
  constructor() {}
  backHome() {
    this.loginService.logout();
  }
}