import { Component, ViewEncapsulation} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BadgeModule } from 'primeng/badge';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-badge',
  standalone: true,
  imports: [FormsModule,BadgeModule,CommonModule],
  templateUrl: './badge.component.html',
  styleUrl: './badge.component.css',
  encapsulation: ViewEncapsulation.None
})
export class BadgeComponent {
  roles = [
    { role: 'admin', skills: ['asure', 'java'] },
    { role: 'user', skills: ['angular', 'html'] }
  ];
  getBadgeStyle(role: { role: string }): any {
    if (role.role === 'admin') {
      return {
        'background-color': '#522A8B',
        'color': 'white !important'
      };
    } else if (role.role === 'user') {
      return {
        'background-color': 'white',
        'color': '#522A8B !important'
      };
    } else {
      // Default style
      return {};
    }
  }
}


