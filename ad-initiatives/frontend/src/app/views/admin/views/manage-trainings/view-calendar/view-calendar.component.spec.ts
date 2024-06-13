import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewCalendarComponent } from './view-calendar.component';

describe('ViewCalendarComponent', () => {
  let component: ViewCalendarComponent;
  let fixture: ComponentFixture<ViewCalendarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewCalendarComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ViewCalendarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
