import { Component, CUSTOM_ELEMENTS_SCHEMA, ViewEncapsulation, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { CardModule } from 'primeng/card';
import { CommonModule } from '@angular/common';
import { ProgressBarModule } from 'primeng/progressbar';
import { AccordionModule } from 'primeng/accordion';
import { MenuItem } from 'primeng/api';
import { TabMenuModule } from 'primeng/tabmenu';
import { TabViewModule } from 'primeng/tabview';
import { SortEvent } from 'primeng/api';
import { TabViewComponent } from '../../../../shared/components/tab-view/tab-view.component';
import { ViewSkillsetComponent } from './resource-details/view-skillset/view-skillset.component';
import { ViewPersonalInfoComponent } from './resource-details/view-personal-info/view-personal-info.component';
import { ResourceDetailsComponent } from './resource-details/resource-details.component';
import { CustomBottonComponent } from './../../../../shared/components/custom-button/custom-button.component';
import { TableComponent } from '../../../../shared/components/table/table.component';
import { FormsModule } from '@angular/forms';
import { BadgeModule } from 'primeng/badge';
// import { ContentCardComponent } from './manage-resources.component';
import { ManageResourcesService } from '../../../../service/manage-resources.service';
import { map, catchError, switchMap, takeUntil } from "rxjs/operators";
import { Subject, forkJoin, of } from "rxjs";
import { AddResourceService } from '../../../../service/add-resource.service';
import { AddUserFormComponent } from '../../components/add-user-form/add-user-form.component';
import { SetTrainingFormComponent } from '../../components/set-training-form/set-training-form.component';
import { SetTrainingService } from '../../../../service/set-training.services';
import { ApproverService } from '../../../../service/approver.service';


@Component({
  selector: 'app-manage-resources',
  standalone: true,
  imports: [CardModule, ProgressBarModule, TabViewModule, TabViewComponent, ViewSkillsetComponent, 
    ViewPersonalInfoComponent,ResourceDetailsComponent,CustomBottonComponent, CommonModule, TableComponent, 
    FormsModule, BadgeModule, AddUserFormComponent, SetTrainingFormComponent ],
  templateUrl: './manage-resources.component.html',
  styleUrl: './manage-resources.component.css',
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  encapsulation: ViewEncapsulation.None,
  providers: [ManageResourcesService, ApproverService]
})
export class ManageResourcesComponent implements OnInit{
  @ViewChild(ResourceDetailsComponent)
  filteredData: any[] = [];
  resourceList: any[] = [];
  certList: any[] = [];
  tableColumn: any[] = [];
  items: MenuItem[] | undefined;
  activeItem: MenuItem | undefined;
  isResource: boolean = false;
  selectedMemberDtl: any = {};
  isOpen: boolean = false;
  isSetTrainingOpen: boolean = false;
  action: string = '';
  actionTypeForSetTraining: string = '';
  
  constructor(
    private router: Router,
    private manageResourcesService: ManageResourcesService,
    private addResourceService: AddResourceService,
    private setTrainingService: SetTrainingService,
    private approverService: ApproverService
  ) { }
  private ngUnsubscribe: Subject<any> = new Subject();
  
  public resourceInfos = {
    lastname: '',
    firstname: '',
    middlename: '',
    suffix: '',
    gender: '',
    emailAddress: '',
    careerStep: '',
    empId: '',
    region: '',
    role: '',
    team: '',
    status: '',
    skills:''
  }

  public resourceCertifications: [] = [];

  ngOnInit(): void {
   
    this.getResources();

    this.resourceList = [];
  
    this.tableColumn = [
      { header: 'Name', field: 'membername' },
      { header: 'Employee #', field: 'employeeNum' },
      { header: 'Role', field: 'roleName' },
      { header: 'Team', field: 'teamName' },
      { header: 'Trainings', field: 'membertrainings' },
      { header: 'Certifications', field: 'certifications' },
      { header: 'Actions', field: 'actions' }
    ];

    this.filteredData = this.resourceList;

  }

  onSort(event: SortEvent){
    console.log('Sorting event: ', event);
  }
  
  onSort2(event: any){
    console.log('Sorting event2: ', event);
  }

  onView(rowData: any){
    console.log('View Item: ', rowData); 
    this.resourceDtl()
    this.selectedMemberDtl = rowData;
    console.log('View selectedMemberDtl: ', this.selectedMemberDtl); 
  }

  onEdit(item: any){
    this.action = 'edit'
    console.log('Edit item: ', item)
    this.isOpen = true;
    console.log(this.isOpen, "heree")
     this.addResourceService.viewResource(item.memberId)
    .subscribe((res: any) => {
      console.log(res, "<<<<<< RES")
      delete res.data['password'];
      delete res.data['certifications'];
      this.resourceInfos = res
    }, err => {
      console.log(err, "<<<<< ERROR")
    });

    this.addResourceService.viewResourceCertification(item.employeeNum)
    .subscribe((res: any) => {
      console.log(res, "<<<<<< RES")
      this.resourceCertifications = res
    }, err => {
      console.log(err, "<<<<< ERROR")
    });
  }

  onDelete(item: any){
    console.log('Delete Item: ', item)
  }

  onSearchChange(value: string) {
     
     this.filteredData = this.resourceList.filter(item => item.trainingTitle.toLowerCase().includes(value.toLowerCase()));
  }

  resourceDtl(){
    this.isResource=true;
  }

  getResources(){
    var cert = [];
    this.manageResourcesService.getResources().subscribe((res: any) => {
      this.resourceList = [];
      
      // console.log("res>>>>>>>>>>>>>>>>>>>>>", res.data);
      const observables = res.data.map((item: any) => {
        console.log("getAllResource>>>>>>>>>>>>>>>>>>>>>", item);
        let _certifications: any[] = [];
    
        return this.manageResourcesService.viewResourceCertification(item.empId).pipe(
          map((certRes: any) => {
            if (certRes && certRes.data) {
              _certifications = certRes.data.map((cert: any) => cert.certificationName);
            }
            return _certifications;
          }),
          catchError(err => {
            console.log(err, "<<<<< ERROR in fetching certifications");
            return of([]);
          }),
          switchMap(() => this.setTrainingService.countUserTrainings(item.memberId).pipe(
            map((count: any) => {
              console.log("item>>>>>>>>>>>>>>>>>>>>>", item);
              return {
                memberId: item.memberId,
                membername: item.membername,
                employeeNum: item.employeeNum,
                roleName: item.roleName,
                teamName: item.teamName,
                membertrainings: item.membertrainings,
                certifications: item.certifications
              };
            }),
            catchError(err => {
              console.log(err, "<<<<< ERROR in counting trainings");
              return of({});
            })
          ))
        );
      });
    
      forkJoin(observables).subscribe((results: any) => {
        this.resourceList = results.filter((result:any) => Object.keys(result).length !== 0); // Filter out empty results
      });
    }, err => {
      console.log(err, "<<<<< ERROR in fetching resources");
    });

    // this.manageResourcesService.getResources().pipe(
    //   takeUntil(this.ngUnsubscribe)
    // ).subscribe((resp) => {
    //   if (resp.status == 'SUCCESS') {
    //     console.log("resp",resp);
    //     //this.resourceList = resp.data;
    //     resp.data.forEach((item: any) => {
    //       let resList = 
    //         { memberId: item.memberId,
    //           membername: item.membername,
    //           employeeNum: item.employeeNum,
    //           roleName: item.roleName,
    //           teamName: item.teamName,
    //           memberTrainings: item.trainings,
    //           certifications: [item.certifications]
    //         };
    //         this.resourceList.push(resList);
    //     });
    //   }
    // }, (error: any) => {
      
  //   // });
  }

  onOpenClick() {
    this.isOpen = true;
  }

  onCloseClick() {
    this.isOpen = false;
  }
  
  isOpenChange(event: any){
    console.log(event, "<<<<<<< event")
    this.isOpen = false;
    this.getResources();
  }

  isSetTrainingOpenChange(event: any) {
    console.log(event, "<<<<<<< event")
    this.isSetTrainingOpen = false;
    this.getResources();
  }

  addResource() {
    this.action = 'add'
    this.isOpen = true;
  }

  // getResources() {
  //   this.manageResourcesService.getResources()
  //   .subscribe((res: any) => {
  //     // this.errMessage="";
  //     this.resourceList = res.data;
  //     this.getMemberCertList
  //     console.log(this.resourceList, "<<<<<< RES")
  //   }, err => {
  //     // this.errMessage = err.error;
  //     console.log(err, "<<<<< ERROR")
  //   });
  // }

  getMemberCertList(v_memberId: string){
    this.approverService.getMemberCertification(v_memberId)
    .subscribe((cert_res: any) => {
      // this.errMessage="";
      this.certList = cert_res.data;
      console.log(this.certList, "<<<<<< RES")
    }, err => {
      // this.errMessage = err.error;
      console.log(err, "<<<<< ERROR")
    });
  }


  backManageResource() {
    this.isResource=false
  }

  showSetTrainingMultipleUserForm() {
    console.log("HAPPENED")
    this.actionTypeForSetTraining = 'multiple';
    this.isSetTrainingOpen = true;
  }
  
}
