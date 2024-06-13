import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CustomBottonComponent } from '../custom-button/custom-button.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-dialog-box',
  standalone: true,
  imports: [CustomBottonComponent, CommonModule],
  templateUrl: './dialog-box.component.html',
  styleUrl: './dialog-box.component.css'
})
export class DialogBoxComponent {
  @Input() imgStatus: string | undefined;
  @Input() msgStatus: string | undefined;
  @Input() msg: string | undefined;
  @Input() isOpen: boolean = false;
  @Output() dialogClosed = new EventEmitter<void>();
  @Output() dialogProceed = new EventEmitter<void>();

  constructor(){}

  ngOnInit(): void {

  }

  onCancelClose() {
    this.isOpen = false
    this.dialogClosed.emit();
  }

  onProceedClose() {
    this.isOpen = false
    this.dialogProceed.emit();
  }

}
