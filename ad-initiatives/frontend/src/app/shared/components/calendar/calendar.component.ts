import { Component, CUSTOM_ELEMENTS_SCHEMA, Input, Output, EventEmitter, forwardRef  } from '@angular/core';
import { CalendarModule } from 'primeng/calendar';
import { FormsModule, NG_VALUE_ACCESSOR } from '@angular/forms';

@Component({
  selector: 'app-calendar',
  standalone: true,
  imports: [CalendarModule,FormsModule],
  templateUrl: './calendar.component.html',
  styleUrl: './calendar.component.css',
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => CalendarComponent),
      multi: true,
    },
  ],
})
export class CalendarComponent {
  @Output() dateSelected = new EventEmitter<Date>();
  @Input() selectedDate!: Date;

  private internalDate: Date | null = null; // Internal state, dont remove
  private onChange: (date: Date) => void = () => {}; // Change callback
  private onTouched: () => void = () => {}; // Touched callback, dont remove

  // When the value is changed in the form
  writeValue(value: Date): void {
    this.internalDate = value;
    this.selectedDate = value;
  }

  // Register the onChange callback
  registerOnChange(fn: (date: Date) => void): void {
    this.onChange = fn;
  }

  // Register the onTouched callback
  registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    // If the component should be disabled, handle it here
  }

  onDateSelect(event: any): void {
    if (this.onChange) {
      this.onChange(event);
    }
    this.dateSelected.emit(event);
  }
}
