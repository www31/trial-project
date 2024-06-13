import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Options } from './options';
import { FormsModule, NgForm } from '@angular/forms';
import { NgClass, NgFor, NgIf } from '@angular/common';

@Component({
  selector: 'app-dropdown',
  standalone: true,
  imports: [FormsModule, NgClass, NgFor, NgIf],
  templateUrl: './dropdown.component.html',
  styleUrl: './dropdown.component.css'
})
export class DropdownComponent {
  public testForm!: NgForm;
  public isDropDownOpen: boolean = false;
  public dropdown: string = '';
  @Input('options') options!: Options[];
  @Output() selectedOption = new EventEmitter<string>();
  ngOnInit() {
    this.options.forEach((opt) => {
      opt.isActive = false;
    });
    this.options.splice(0, 0, { value: '', isActive: true });
  }

  toggleDropdown() {
    this.isDropDownOpen = !this.isDropDownOpen;
  }

  selectOption(evt: any, optionIndex: number) {
    this.toggleDropdown();
    this.options.forEach((opt: any, index: number) => {
      opt.isActive = optionIndex === index;
    });
    this.dropdown = evt.target.innerHTML;
    this.selectedOption.emit(this.dropdown.trim());
  }
}
