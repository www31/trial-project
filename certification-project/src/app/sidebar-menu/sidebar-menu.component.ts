import { AfterViewInit, Component, ElementRef, ViewChild, ViewEncapsulation } from '@angular/core';

@Component({
  selector: 'app-sidebar-menu',
  standalone: true,
  imports: [],
  templateUrl: './sidebar-menu.component.html',
  styleUrl: './sidebar-menu.component.css',
})
export class SidebarMenuComponent implements AfterViewInit {
  ngAfterViewInit(): void {
    const body = document.querySelector('body');
    const sidebar = body?.querySelector('.sidebar');
    const toggle = body?.querySelector('.bottom-content');
    const searchBtn = body?.querySelector('.search-box');
    toggle?.addEventListener('click', () => {
      sidebar?.classList.toggle("close");
    });
    searchBtn?.addEventListener('click', () => {
      sidebar?.classList.remove("close");
    });
  }
}
