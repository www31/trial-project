import { Component, signal, ChangeDetectorRef, OnInit, CUSTOM_ELEMENTS_SCHEMA, ViewEncapsulation, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { FullCalendarModule } from '@fullcalendar/angular';
import { CalendarOptions, EventApi } from '@fullcalendar/core';
import interactionPlugin from '@fullcalendar/interaction';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import listPlugin from '@fullcalendar/list';
import { ManageTrainingService } from '../../../../../service/manage-training.service';
import { BlockUIModule } from 'primeng/blockui';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { PanelModule } from 'primeng/panel';

@Component({
  selector: 'app-view-calendar',
  standalone: true,
  imports: [CommonModule, RouterOutlet, FullCalendarModule, BlockUIModule, ProgressSpinnerModule, PanelModule],
  templateUrl: './view-calendar.component.html',
  styleUrl: './view-calendar.component.css',
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  encapsulation: ViewEncapsulation.None
})
export class ViewCalendarComponent implements OnInit, AfterViewInit {
  calendarVisible = signal(false);
  calendarOptions !: CalendarOptions
  currentEvents = signal<EventApi[]>([]);
  errMessage: any;
  schedule: any;
  ngOnInit(): void {
    setTimeout(() => {
      this.calendarVisible = signal(false)
      this.intializeTable();
    }, 2500);
    
  }
  ngAfterViewInit() {
  }


  constructor(private changeDetector: ChangeDetectorRef,
    private manageTrainingService: ManageTrainingService
  ) {
  }

  intializeTable() {
    this.calendarVisible = signal(false)
    this.manageTrainingService.getCalendarSchedule()
      .subscribe((res: any) => {
        this.errMessage = "";
        console.log(res, "<<<<<< RESA")
        //this.schedule = res.data
        this.schedule = res.data.filter(function (item: any) {
          item['id'] = item.trainingId
          item['title'] = item.trainingName
          item['start'] = item.startDate
          item['end'] = item.dueDate
        })
        console.log(res.data, "<<<<<< this.schedule")

        this.calendarOptions = {
          plugins: [
            interactionPlugin,
            dayGridPlugin,
            timeGridPlugin,
            listPlugin,
          ],
          headerToolbar: {
            left: 'timeGridDay,timeGridWeek,dayGridMonth',
            center: 'title',
            right: ''
          },
          initialView: 'dayGridMonth',
          events: res.data,
          weekends: true,
          selectable: true,
          height: 600,
        };
        setTimeout(() => {
          this.calendarVisible = signal(true)
        }, 2500);
      }, err => {
        setTimeout(() => {
          this.calendarVisible = signal(true)
        }, 2500);
        this.errMessage = err.error;
        console.log(err, "<<<<< ERROR")
      });
  }

}
