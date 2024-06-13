import { Component, Input, CUSTOM_ELEMENTS_SCHEMA,ViewEncapsulation } from '@angular/core';
import { TabViewModule } from 'primeng/tabview';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-tab-view',
  standalone: true,
  imports: [TabViewModule,CommonModule],
  templateUrl: './tab-view.component.html',
  styleUrl: './tab-view.component.css',
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  encapsulation: ViewEncapsulation.None
})
export class TabViewComponent {
  @Input() tabs: any[] = [];

  constructor() { }

  ngOnInit(): void {
    console.log(this.tabs)
    console.log(this.isArray(), "<<<<<<")
  }

  isArray(): boolean {
    return Array.isArray(this.tabs);
  }
 
}
