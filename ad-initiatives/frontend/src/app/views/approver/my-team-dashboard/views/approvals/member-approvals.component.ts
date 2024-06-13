import { Component, OnInit, Input, ViewEncapsulation } from '@angular/core';
import { TableComponent } from '../../../../../shared/components/table/table.component';
import { ApproverService } from '../../../../../service/approver.service';
import { takeUntil } from "rxjs/operators";
import { Subject } from "rxjs";
import { CommonModule } from '@angular/common';


@Component({
  selector: 'approver-member-approvals',
  standalone: true,
  imports: [TableComponent, CommonModule],
  templateUrl: './member-approvals.component.html',
  styleUrl: './member-approvals.component.css',
  providers: [ApproverService],
  encapsulation: ViewEncapsulation.None
})
export class MemberApprovalsComponent implements OnInit {

  @Input() selectedData: any;

  tableColumn: any[] = [];  
  approvalData: any[] = [];

  constructor(
    private approverSvc: ApproverService,
  ) {}
  private ngUnsubscribe: Subject<any> = new Subject();

  ngOnInit(): void {
    this.getTrainingsForApproval(this.selectedData.memberId);
    this.tableColumn = [
      { header: 'CERTIFICATION', field: 'certName' },
      { header: '', field: 'actions', width: '300px' }
    ];
  }

  getTrainingsForApproval(v_memberId: string){
    console.log("getTrainingsForApproval", v_memberId);
    this.approverSvc.getTrainingsForApproval(v_memberId).pipe(
      takeUntil(this.ngUnsubscribe)
    ).subscribe((resp) => {
      if (resp.status == 'SUCCESS') {
        console.log("resp",resp);
        this.approvalData = resp.data;
      }
    }, (error: any) => {
      
    });
  }

}
