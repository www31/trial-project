import { Component, OnInit } from '@angular/core';
import { CardModule } from 'primeng/card';
import { CommonModule } from '@angular/common';
import { ApproverService } from '../../../service/approver.service';
import { takeUntil } from "rxjs/operators";
import { Subject } from "rxjs";
import {  ViewMemberDetailsComponent } from '../../approver/my-team-dashboard/views/view-member-details.component';

@Component({
  selector: 'approver-team-dashboard',
  standalone: true,
  imports: [CardModule, CommonModule, ViewMemberDetailsComponent],
  templateUrl: './my-team-dashboard.component.html',
  styleUrl: './my-team-dashboard.component.css',
  providers: [ApproverService],
})
export class MyTeamDashboardComponent implements OnInit {

  userData: any[] = [];
  filteredUserData: any[] = [];
  isPopupVisible: boolean = false;
  selectedData: any[] = [];

  constructor(
    private approverSvc: ApproverService,
  ) {}
  private ngUnsubscribe: Subject<any> = new Subject();

  ngOnInit(): void {
    this.getTeamMemberList(localStorage.getItem('USER_NAME'));
  }

  onInputKeyChange(event: any) {
    this.filteredUserData = this.userData.filter(item => item.fullName.toUpperCase().match(event.target.value.toUpperCase()));
  }

  getTeamMemberList(v_userId: any){
    this.approverSvc.getTeamMemberList(v_userId).pipe(
      takeUntil(this.ngUnsubscribe)
    ).subscribe((resp) => {
      if (resp.status == 'SUCCESS') {
        console.log("resp",resp);
        this.filteredUserData = resp.data;
        this.userData = this.filteredUserData;
      }
    }, (error: any) => {
      
    });
  }

  onViewMemberDetails(v_userData: any) {
    this.selectedData = v_userData;
    this.isPopupVisible = true;
  }

  closePopup() {
    this.isPopupVisible = false;
  }

}
