import { Component, OnInit, Input, Output, EventEmitter, ViewEncapsulation} from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { CustomBottonComponent } from './../../../../shared/components/custom-button/custom-button.component';
import { AccordionComponent } from './../../../../shared/components/accordion/accordion.component';
import { MemberCerificateComponent } from './certificates/member-certificate.component';
import { MemberApprovalsComponent } from './approvals/member-approvals.component';
import { MemberTrainingsComponent } from './trainings/member-trainings.component';


@Component({
  selector: 'view-member-details',
  standalone: true,
  imports: [ CommonModule,CustomBottonComponent, AccordionComponent, 
    MemberCerificateComponent, MemberApprovalsComponent, MemberTrainingsComponent],
  templateUrl: './view-member-details.component.html',
  styleUrl: './view-member-details.component.css' ,
  encapsulation: ViewEncapsulation.None
 
})

export class ViewMemberDetailsComponent implements OnInit{
  @Input() isPopupVisible: boolean = false;
  @Output() close = new EventEmitter<void>();
  @Input() selectedData: any;

  public hdrCert = ['Certifications'];
  public hdrApprovals = ['For Approvals'];
  public hdrTrainings = ['Trainings'];

  back: string  = 'Back'
  constructor(private router: Router) { }

  ngOnInit(): void {      
    console.log("selectedData", this.selectedData);
  }     

  closePopup() {
    this.isPopupVisible = false;
    this.close.emit();
  }

  }