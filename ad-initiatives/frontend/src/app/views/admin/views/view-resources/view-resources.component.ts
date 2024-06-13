import { Component, OnInit, Input, Output, EventEmitter,CUSTOM_ELEMENTS_SCHEMA, ViewEncapsulation } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { CustomBottonComponent } from './../../../../shared/components/custom-button/custom-button.component';
import {  ReportResourcesComponent } from '../report-resources/report-resources.component';
import { TabViewModule } from 'primeng/tabview';
import { TabViewComponent } from '../../../../shared/components/tab-view/tab-view.component';
import { TableComponent } from '../../../../shared/components/table/table.component';
import { CardModule } from 'primeng/card';

@Component({
  selector: 'view-resources',
  standalone: true,
  imports: [CommonModule,CustomBottonComponent, TabViewModule, TabViewComponent,TableComponent, CardModule ],
  templateUrl: './view-resources.component.html',
  styleUrl: './view-resources.component.css',
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  encapsulation: ViewEncapsulation.None
})
export class ViewResourcesComponent implements OnInit{
  @Input() isVisible?: boolean;
  @Input() data: any;
  @Output() close = new EventEmitter<void>();   
  @Output() toggleShowReportResources = new EventEmitter<void>();   
  @Input() showViewResources: boolean = false;
  @Output() showViewResourcesChange = new EventEmitter<boolean>();  
  @Input()  resourceList: any[] = [];
  filteredData: any[] = []; 
  tableColumn: any[] = [];  
  tableColumn2: any[] = [];  
  completedList: any[] = [];
  notcompletedList: any[] =[];
  back: string  = 'Back'
    constructor(private router: Router) { }

  ngOnInit(): void {      
    console.log('dsd', this.data);
    this.tableColumn = [       
      { header: 'Certification Name', field: 'certName'},
      { header: 'Certification Link', field: 'certLink'},
      { header: 'Expiration Date', field: 'expiryDate'}     
    ];

    this.tableColumn2 = [       
      { header: 'Certification Name', field: 'certName'},
      { header: 'Started Date', field: 'startDate'},
      { header: 'End Date', field: 'dueDate'},   
      { header: 'Status', field: 'status'},  
    ];

    // this.filteredData = this.resourceList;
    this.completedList = this.resourceList.filter(resource => resource.status === 'Completed' && resource.memberId === this.data.memberId && resource.certId === this.data.certId);
    this.notcompletedList = this.resourceList.filter(resource => resource.status !== 'Completed' && resource.memberId === this.data.memberId && resource.certId === this.data.certId);
  }   
  
  onClickToggleB(){
    this.showViewResources = !this.showViewResources;
    this.showViewResourcesChange.emit(this.showViewResources);
    
  }

  onClickDownload(){

  }

  


}
