import { Component, OnInit, ViewEncapsulation, Input } from '@angular/core';
import { TableComponent } from '../../../../../shared/components/table/table.component';
import { CustomBottonComponent } from '../../../../../shared/components/custom-button/custom-button.component';
import { CommonModule } from '@angular/common';
import { ApproverService } from '../../../../../service/approver.service';
import { takeUntil } from "rxjs/operators";
import { Subject } from "rxjs";

@Component({
  selector: 'approver-member-trainings',
  standalone: true,
  imports: [TableComponent, CustomBottonComponent,CommonModule],
  templateUrl: './member-trainings.component.html',
  styleUrl: './member-trainings.component.css',
  encapsulation: ViewEncapsulation.None,
  providers: [ApproverService]
})
export class MemberTrainingsComponent implements OnInit {

  @Input() selectedData: any;

  tableColumn: any[] = [];
  trainingData: any[] = [];
  unFilteredData: any[] = [];
  filteredNSData: any[] = [];
  filteredIPData: any[] = [];
  filteredCompData: any[] = [];
  filteredOvrData: any[] = [];


  public notStartedFlg: boolean = false;
  public inProgressFlg: boolean = false;
  public completedFlg: boolean = false;
  public overdueFlg: boolean = false;
  public StatusName = {
    NOT_STARTED: 'Not Yet Started',
    IN_PROGRESS: 'In Progress',
    COMPLETED: 'Completed',
    OVERDUE: 'Overdue'
}
  
  constructor(
    private approverSvc: ApproverService,
  ) {}
  private ngUnsubscribe: Subject<any> = new Subject();

  ngOnInit(): void {
    this.getMemberTrainingList(this.selectedData.memberId);
    this.tableColumn = [
      { header: 'TRAINING NAME', field: 'trainingName' }, 
      { header: 'STATUS', field: 'statusName' },
      { header: 'TIME SPENT', field: 'timeSpent' }
    ];
  }

  onClickNotStarted(){
    if(this.notStartedFlg){
      this.notStartedFlg = false;
      this.clearFilter();
    }else{
      this.notStartedFlg = true;
      this.trainingData = this.filteredNSData;
    }
  }

  onClickInProgress(){
    if(this.inProgressFlg){
      this.inProgressFlg = false;
      this.clearFilter();
    }else{
      this.inProgressFlg = true;
      this.trainingData = this.filteredIPData;
    }
  }

  onClickCompleted(){
    if(this.completedFlg){
      this.completedFlg = false;
      this.clearFilter();
    }else{
      this.completedFlg = true;
      this.trainingData = this.filteredCompData;
    }
  }

  onClickOverdue(){
    if(this.overdueFlg){
      this.overdueFlg = false;
      this.clearFilter();
    }else{
      this.overdueFlg = true;
      this.trainingData = this.filteredOvrData;
    }
  }

  getMemberTrainingList(v_memberId: string){
    this.approverSvc.getMemberTrainingList(v_memberId).pipe(
      takeUntil(this.ngUnsubscribe)
    ).subscribe((resp) => {
      if (resp.status == 'SUCCESS') {
        console.log("resp",resp);
        this.trainingData = resp.data;
        this.unFilteredData = resp.data;
        this.filteredNSData = this.trainingData.filter(item => item.statusName.match(this.StatusName.NOT_STARTED));
        this.filteredIPData = this.trainingData.filter(item => item.statusName.match(this.StatusName.IN_PROGRESS));
        this.filteredCompData = this.trainingData.filter(item => item.statusName.match(this.StatusName.COMPLETED));
        this.filteredOvrData = this.trainingData.filter(item => item.statusName.match(this.StatusName.OVERDUE));
      }
    }, (error: any) => {
      
    });
  }

  filterTrainingData(){

  }

  clearFilter(){
    this.trainingData = this.unFilteredData;
  }

}
