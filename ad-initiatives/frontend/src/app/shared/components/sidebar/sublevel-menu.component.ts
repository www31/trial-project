import { Component, OnInit, Input } from '@angular/core';
import { INavbarData } from './helper';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { trigger, state, style, transition, animate } from '@angular/animations';


@Component({
  selector: 'app-sublevel-menu',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <ul *ngIf="data.items && data.items.length > 0"
    [@submenu] = "expanded 
      ? {value: 'visible', params: {transitionParams: '400ms cubic-bezier(0.86, 0, 0.07, 1)', height: '*'}}
      : {value: 'hidden',
          params:  {transitionParams: '400ms cubic-bezier(0.86, 0, 0.07, 1)', height: '0'}
        }"
      class="sublevel-nav"
    >
      <li *ngFor="let item of data.items" class="sublevel-nav-item">
        <a class="sublevel-nav-link"
        (click)="handleClick(item)"
          *ngIf="item.items && item.items.length > 0"
        >
          <i class="sublevel-link-icon pi pi-circle"></i>  
          <span class="sublevel-link-text" >{{item.label}}</span>
          <i class="menu-collapse-icon" *ngIf="item.items"
            [ngClass]="!item.expanded ? 'pi pi-caret-right' : 'pi pi-caret-down'"
          ></i>
        </a>
        <a class="sublevel-nav-link"
          *ngIf="!item.items || (item.items && item.items.length == 0)"
          [routerLink]="[item.routeLink]" 
          routerLinkActive="active-sublevel" 
          [routerLinkActiveOptions]="{exact: true}"
        >
          <i class="sublevel-link-icon pi pi-circle"></i>  
          <span class="sublevel-link-text" >{{item.label}}</span>
        </a>
        <div *ngIf="item.items && item.items.length > 0">
          <app-sublevel-menu
            [data]="item"
            [collapsed] = "collapsed"
            [multiple]="multiple"
            [expanded]="item.expanded"
          >

          </app-sublevel-menu>
        </div>
      </li>
    </ul>
  `,
  styleUrl: './sidebar.component.css',
  animations: [
    trigger('submenu', [
      state('hidden', style({
        height: '0',
        overflow: 'hidden'
      })),
      state('visible', style({
        height: '*'
      })),
      transition('visible <=> hidden', [
        style({ overflow: 'hidden' }),
        animate('{{transitionParams}}')
      ])
    ])
  ]
})
export class SublevelMenuComponent implements OnInit{
  
  @Input() data: INavbarData = {
    accesslevel:'',
    routeLink: '',
    icon: '',
    label: '',
    items: []
  }
  @Input() collapsed = false;
  @Input() animating: boolean | undefined;
  @Input() expanded: boolean | undefined;
  @Input() multiple: boolean = false;

  
  ngOnInit() : void {

  }

  handleClick(item: any): void {
    if(!this.multiple) {
      if (this.data.items && this.data.items.length > 0) {
        for(let modelItem of this.data.items){
          modelItem.expanded = false;
        }
      }
    }
    item.expanded = !item.expanded; 
  }
}