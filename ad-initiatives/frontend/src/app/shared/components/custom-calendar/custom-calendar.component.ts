import { CUSTOM_ELEMENTS_SCHEMA, Component, EventEmitter, Input, Output, forwardRef } from '@angular/core';
import { FormsModule, NG_VALUE_ACCESSOR } from '@angular/forms';
import { CalendarComponent } from '../calendar/calendar.component';
import { CalendarModule } from 'primeng/calendar';

@Component({
  selector: 'app-custom-calendar',
  standalone: true,
  imports: [CalendarModule,FormsModule],
  templateUrl: './custom-calendar.component.html',
  styleUrl: './custom-calendar.component.css',
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => CustomCalendarComponent),
      multi: true,
    },
  ],
})
export class CustomCalendarComponent {
  selectedDate!: Date;
  @Output() dateSelected = new EventEmitter<{ date: Date, context?: any }>();
  @Input() context?: any;

  onDateSelect(event: any) {
    const selectedDate: Date = event;
    this.dateSelected.emit({ date: selectedDate, context: this.context });
  }
}
