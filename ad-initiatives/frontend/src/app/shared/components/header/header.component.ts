import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Component, inject } from '@angular/core';
import { PanelModule } from 'primeng/panel';
import { CommonModule } from '@angular/common';
import { LoginService } from '../login/login.services';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [PanelModule, CommonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  loginService = inject(LoginService)
  toggleCheck: boolean = false;

  signOut() {
    this.loginService.logout();
  }

  checkValue(evt: any)  {
    return this.toggleCheck = evt.target.checked;
  }

  toggleClick() {
    return this.toggleCheck ? 'logout-hidden' : 'logout-visible';
  }
}
