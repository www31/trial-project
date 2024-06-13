import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewSkillsetComponent } from './view-skillset.component';

describe('ViewSkillsetComponent', () => {
  let component: ViewSkillsetComponent;
  let fixture: ComponentFixture<ViewSkillsetComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewSkillsetComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ViewSkillsetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
