import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewResourcesComponent } from './view-resources.component';

describe('ViewResourcesComponent', () => {
  let component: ViewResourcesComponent;
  let fixture: ComponentFixture<ViewResourcesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewResourcesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ViewResourcesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
