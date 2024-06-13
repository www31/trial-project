import { Component, CUSTOM_ELEMENTS_SCHEMA, ViewEncapsulation, OnInit, Input } from '@angular/core';
import { CardModule } from 'primeng/card';
import { ProgressBarModule } from 'primeng/progressbar';
import { CommonModule } from '@angular/common';
import { AccordionModule } from 'primeng/accordion';
import { MenuItem } from 'primeng/api';
import { TabMenuModule } from 'primeng/tabmenu';
import { TabViewModule } from 'primeng/tabview';
import { TabViewComponent } from '../../../../../shared/components/tab-view/tab-view.component';
import { ViewSkillsetComponent } from './../resource-details/view-skillset/view-skillset.component';
import { ViewPersonalInfoComponent } from './../resource-details/view-personal-info/view-personal-info.component';
import { ViewCertificationComponent } from './view-certification/view-certification.component';
import { CustomBottonComponent } from '../../../../../shared/components/custom-button/custom-button.component';
import { ViewTrainingDetailsComponent } from './view-training-details/view-training-details.component';

@Component({
  selector: 'app-resource-details',
  standalone: true,
  imports: [CardModule, ProgressBarModule, TabViewModule, TabViewComponent, ViewSkillsetComponent, ViewPersonalInfoComponent, ViewCertificationComponent,CommonModule,
    CustomBottonComponent, ViewTrainingDetailsComponent
  ],
  templateUrl: './resource-details.component.html',
  styleUrl: './resource-details.component.css',
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  encapsulation: ViewEncapsulation.None
})
export class ResourceDetailsComponent implements OnInit{

  @Input() memberDtl: any = {};

  activeIndex: number = 0;

  goBack(): void {
    if (this.activeIndex > 0) {
      this.activeIndex--;
    }
  }

  ngOnInit(): void {
    console.log(this.memberDtl)
  }
}