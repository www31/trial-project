import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { FooterComponent } from './shared/components/footer/footer.component';
import { HeaderComponent } from './shared/components/header/header.component';
import { LoginComponent } from './shared/components/login/login.component';
import { LoginService } from './shared/components/login/login.services';
import { SidebarComponent } from './shared/components/sidebar/sidebar.component';
import { AdminComponent } from './views/admin/admin.component';
import { BodyComponent } from './views/admin/views/body/body.component';
import { ApproverComponent } from './views/approver/approver.component';
import { UserComponent } from './views/user/user.component';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet, 
    AdminComponent, 
    ApproverComponent, 
    UserComponent, 
    HttpClientModule, 
    BodyComponent,
    SidebarComponent,
    HeaderComponent,
    FooterComponent,
    LoginComponent,
    CommonModule
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'ad-initiative';
  isLoggedIn: boolean = false;
  loginService = inject(LoginService);
  constructor() {
    this.isLoggedIn = this.loginService.isLoggedIn();
    if (this.isLoggedIn) {
      let accessName = this.loginService.getAccessLevel();
      this.loginService.findHomePagebyRole(accessName!);
    } 
  }
}