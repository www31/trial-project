import { Component, OnInit } from '@angular/core';
import { SortEvent } from 'primeng/api';
import { CustomBottonComponent } from '../../../../shared/components/custom-button/custom-button.component';
import { TableComponent } from '../../../../shared/components/table/table.component';
import { ResourceDataModel } from '../../../../models/resourcedata.model';
import { DialogBoxComponent } from '../../../../shared/components/dialog-box/dialog-box.component';
import { CommonModule } from '@angular/common';
import { CardModule } from 'primeng/card';
import { ResourceDataService } from '../../../../service/report-resource.service';
import { ViewResourcesComponent } from '../view-resources/view-resources.component';


@Component({
  selector: 'app-report-cresources',
  standalone: true,
  imports: [TableComponent, CustomBottonComponent, DialogBoxComponent,  CommonModule, CardModule, ViewResourcesComponent],
  templateUrl: './report-resources.component.html',
  styleUrl: './report-resources.component.css'
})
export class ReportResourcesComponent {
  filteredData: any[] = [];
  resourceList: any[] = [];
  tableColumn: any[] = [];  
  isOpen: boolean = false;
  isSuccess: boolean = false;
  viewData: any[] = [];
  public errMessage: any;
  showViewResources = true;
  selectedId: string = "";
  isPopupVisible: boolean = false;
  isResource: boolean = false;

  constructor(
    private resourceDataservice: ResourceDataService,
    ) { }
  
  
    ngOnInit(): void {
      this.getResources();
  
      this.tableColumn = [       
        { header: 'ID Number', field: 'employeeNum', width: '150px'},
        { header: 'Name', field: 'memberName', width: '300px'},
        { header: 'Team', field: 'teamName', width: '250px' },
        { header: 'For Certifications', field: 'forCertification', width: '300px' },
        { header: 'Upcoming Certifications', field: 'upcomingCertication', width: '250px' },
        { header: 'Ongoing Trainings', field: 'productName', width: '300px' },
        { header: 'Overdue Certifications', field: 'delayed', width: '250px'  },
        { header: 'Certifications', field: 'certName', width: '300ppx' },
        { header: 'Action', field: 'actions', width: '300px' }
      ];
  
      this.filteredData = this.resourceList;
    }
  
    getResources() {
      this.resourceDataservice.getRecordsList()
      .subscribe((res: any) => {
        this.errMessage="";
        this.resourceList = res.data;
        console.log(this.resourceList, "<<<<<< RES")
      }, err => {
        this.errMessage = err.error;
        console.log(err, "<<<<< ERROR")
      });
    }
  
    onSort(event: SortEvent){
      console.log('Sorting event: ', event);
    }
  
    
    onSort2(event: any){
      console.log('Sorting event2: ', event);
    }
  
    toggleShowViewReources() {
      this.showViewResources = !this.showViewResources;
      console.log('show', this.showViewResources);
      console.log('is resource - label', this.isResource);
      if (this.showViewResources) {
        this.isResource = false;
      } else {
        this.isResource = true;
      }
    }
  
    
    onSearchChange(value: string) {  
      this.filteredData = this.resourceList.filter(item => item.memberName.toLowerCase().includes(value.toLowerCase()));
    }    

    onView(rowData: any){
      console.log('View Item: ', rowData);
      this.resourceDtl()
      this.viewData = rowData;
      this.toggleShowViewReources(); 
    }

    resourceDtl(){
      this.isResource=true;
    }

    backReportResource(){
      this.isResource=false
    }



}

