import { Component, EventEmitter, HostListener, OnInit, Output, inject } from '@angular/core';
import { navbarData } from './nav-data';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { SublevelMenuComponent } from './sublevel-menu.component';
import { INavbarData } from './helper';
import { AccessLevel } from '../../constants/access-level'
import { LoginService } from '../login/login.services';


interface SidebarToggle {
  screenWidth: number;
  collapsed: boolean;
}
@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterModule, CommonModule, SublevelMenuComponent],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent implements OnInit {
  loginService = inject(LoginService);
  @Output() onToggleSidebar: EventEmitter<SidebarToggle> = new EventEmitter();
  collapsed = false;
  screenWidth = 0;
  //navData = navbarData;
  navData: any;
  multiple: boolean = false;
  
  @HostListener('window:resize', ['$event'])
  onResize(event: any){
    this.screenWidth = window.innerWidth;
    if(this.screenWidth = 768) {
      this.collapsed = false;
      this.onToggleSidebar.emit({collapsed: this.collapsed, screenWidth: this.screenWidth});
    }
  }

  ngOnInit(): void {
      let accessLevel =
        AccessLevel.USER == 
          this.loginService.getAccessLevel() ?
          AccessLevel.USER : 
          (AccessLevel.ADMIN == 
            this.loginService.getAccessLevel() ? 
            AccessLevel.ADMIN : 
            AccessLevel.APPROVER);
      this.screenWidth = window.innerWidth;
      this.getAccessLevelScr(accessLevel);
  }

  toggleCollapse(): void {
    this.collapsed = !this.collapsed;
    this.onToggleSidebar.emit({collapsed: this.collapsed, screenWidth: this.screenWidth});
  }

  closeSidebar(): void {
    this.collapsed = true;
    this.onToggleSidebar.emit({collapsed: this.collapsed, screenWidth: this.screenWidth});
  }

  handleClick(item: INavbarData): void {
    if(!this.multiple){
      for(let modelItem of this.navData) {
        if(item !== modelItem && modelItem.expanded){
          modelItem.expanded = false;
        }
      }
    }
    item.expanded = !item.expanded
  }

  getAccessLevelScr(AccessLevelType: string){
    this.navData = navbarData.filter(item => item.accesslevel.match(AccessLevelType));
  }
}