import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CertificationComponent } from './certification/certification.component';
import { SidebarMenuComponent } from './sidebar-menu/sidebar-menu.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CertificationComponent, SidebarMenuComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {

}
