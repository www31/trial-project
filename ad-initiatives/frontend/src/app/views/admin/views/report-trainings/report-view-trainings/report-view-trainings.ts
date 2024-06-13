import { Component, Input, ViewChild } from '@angular/core';
import { CardModule } from 'primeng/card';
import { MenuItem } from 'primeng/api';
import { Router } from '@angular/router';
import { Subject } from "rxjs";
import { SortEvent } from 'primeng/api';
import { TableComponent } from '../../../../../shared/components/table/table.component';

@Component({
  selector: 'app-report-trainings',
  standalone: true,
  imports: [ TableComponent, CardModule ],
  templateUrl: './report-trainings.component.html',
  styleUrl: './report-trainings.component.css'
})
export class ReportTrainingsComponent {
  
  @Input() data: any;
  // @ViewChild(ResourceDetailsComponent)
  filteredData: any[] = [];
  resourceList: any[] = [];
  tableColumn: any[] = [];
  items: MenuItem[] | undefined;
  activeItem: MenuItem | undefined;
  isResource: boolean = false;
  selectedMemberDtl: any = {};
  
  constructor(
    private router: Router,
    // private manageResourcesService: ManageResourcesService,
  ) { }
  private ngUnsubscribe: Subject<any> = new Subject();
  
  ngOnInit(): void {
   
    // this.getResources();

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
    console.log('Edit item: ', item)
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

}
