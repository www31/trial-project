import { Component } from '@angular/core';

import { HeaderComponent } from '../../shared/components/header/header.component';
import { CustomBottonComponent } from '../../shared/components/custom-button/custom-button.component';

import { Router, RouterOutlet } from '@angular/router';
import { FooterComponent } from '../../shared/components/footer/footer.component';
import { certificationInfo } from '../../shared/constants/info-card.constant';
import { InfoCardComponent } from '../../shared/components/info-card/info-card.component';
import { PanelModule } from 'primeng/panel';
import { CardModule } from 'primeng/card';
import { ChartModule } from 'primeng/chart';
import { SidebarComponent } from '../../shared/components/sidebar/sidebar.component';
import { ManageResourcesComponent } from './views/manage-resources/manage-resources.component';
import { AdminDashboardComponent } from '../../views/admin/views/admin-dashboard/admin-dashboard.component';
import { BodyComponent } from './views/body/body.component';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [RouterOutlet, CustomBottonComponent, HeaderComponent, FooterComponent, PanelModule, CardModule, ChartModule, ManageResourcesComponent,
    AdminDashboardComponent, SidebarComponent, BodyComponent, InfoCardComponent],
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.css'
})
export class AdminComponent {
  back: string = 'Back';
  data: any;
  constructor(private router: Router) { }
  
  ngOnInit(): void {
    this.data = certificationInfo;
  }

  backHome() {
    this.router.navigate(['/']);
  }
}
