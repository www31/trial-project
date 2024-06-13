import { Component, CUSTOM_ELEMENTS_SCHEMA, ViewEncapsulation, Input } from '@angular/core';
import { AccordionModule } from 'primeng/accordion';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-accordion',
  standalone: true,
  imports: [AccordionModule,CommonModule],
  templateUrl: './accordion.component.html',
  styleUrl: './accordion.component.css',
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  encapsulation: ViewEncapsulation.None
})
export class AccordionComponent {
  @Input() headerTexts: string[] = []
}


