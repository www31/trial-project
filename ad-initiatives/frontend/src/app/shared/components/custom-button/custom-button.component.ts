import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-custom-button',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './custom-button.component.html',
  styleUrl: './custom-button.component.css'
})
export class CustomBottonComponent implements OnInit{
  @Input() backgroundColor: string = 'var(--purple)';
  @Input() textColor: string = 'var(--white)';
  @Input() width: string = 'auto'
  @Input() margin: string = 'auto';
  @Input() label: string = 'Next';
  @Input() disabled: boolean = false;
  
  @Input() icon: string = 'pi pi-angle-double-right';
  @Output() buttonClicked = new EventEmitter<void>();
  constructor() { }

  ngOnInit(): void {
    
  }

  onClick(){
    this.buttonClicked.emit();
  }
}
