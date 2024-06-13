import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { TableComponent } from '../../../../../shared/components/table/table.component';
import { ApproverService } from '../../../../../service/approver.service';
import { takeUntil } from "rxjs/operators";
import { Subject } from "rxjs";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'approver-member-certificate',
  standalone: true,
  imports: [TableComponent, CommonModule],
  templateUrl: './member-certificate.component.html',
  styleUrl: './member-certificate.component.css',
  providers: [ApproverService],
})
export class MemberCerificateComponent implements OnInit {

  @Input() selectedData: any;

  tableColumn: any[] = [];  
  memberCertData: any[] = [];

  constructor(
    private approverSvc: ApproverService,
  ) {}
  private ngUnsubscribe: Subject<any> = new Subject();

  ngOnInit(): void {
    this.getMemberCertification(this.selectedData.memberId);
    this.tableColumn = [
      { header: 'CERTIFICATION', field: 'certName' }
    ];
  }

  getMemberCertification(v_memberId: string){
    this.approverSvc.getMemberCertification(v_memberId).pipe(
      takeUntil(this.ngUnsubscribe)
    ).subscribe((resp) => {
      if (resp.status == 'SUCCESS') {
        console.log("resp",resp);
        this.memberCertData = resp.data;
      }
    }, (error: any) => {
      
    });
  }

}
