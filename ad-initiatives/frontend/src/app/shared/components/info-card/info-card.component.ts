import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-info-card',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './info-card.component.html',
  styleUrl: './info-card.component.css'
})
export class InfoCardComponent {
  @Input() certificationInfo: any;

  ngOnInit(): void {
    console.log(this.isArray(), "<<<<<<")
  }
 

  isArray(): boolean {
    return Array.isArray(this.certificationInfo);
  }
}
