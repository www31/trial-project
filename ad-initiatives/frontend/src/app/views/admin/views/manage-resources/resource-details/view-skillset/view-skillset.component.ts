import { Component, ViewChild, Input  } from '@angular/core';
import { DropdownComponent } from '../../../../../../shared/components/dropdown/dropdown.component';
import { CommonModule } from '@angular/common';
import { BadgeModule } from 'primeng/badge';
import { DropdownModule } from 'primeng/dropdown';
import { AdminService } from '../../../../../../service/admin.service';
import { takeUntil } from "rxjs/operators";
import { Subject } from "rxjs";
import { SkillDetail} from '../../../../../../models/skills.model';

@Component({
  selector: 'app-view-skillset',
  standalone: true,
  imports: [DropdownComponent,CommonModule, BadgeModule, DropdownModule],
  templateUrl: './view-skillset.component.html',
  styleUrl: './view-skillset.component.css',
  providers: [AdminService],
})
export class ViewSkillsetComponent {
  @Input() memberDtl: any = {};
  @ViewChild(DropdownComponent)
  dropdownComponent!: DropdownComponent;
  fiterOption:any='All';
  skillOption:any=[]
  public selectedType:any='All'
  badgeLabel: any

  constructor(
    private adminService: AdminService,
  ) {}
  private ngUnsubscribe: Subject<any> = new Subject();
  public incident: SkillDetail = new SkillDetail();

  frontEnd : any = []
  backEnd : any = []
  testingTools : any = []

  ngOnInit() {
    this.getMemberSkillSet();
    this.skillOption= [
      { label: 'Front End', value: 'Front End' },
      { label: 'Back End', value: 'Back End' },
      { label: 'Testing Tools', value: 'Testing Tools' },
      { label: 'All', value: 'All' },
    ];
    console.log(this.memberDtl)
  }

  onDropdownChange(event: string) {
    console.log( event);
    this.fiterOption = event.toString();
    console.log( this.fiterOption);
  }

  getMemberSkillSet(){
    this.adminService.getMemberSkillSet(this.memberDtl.memberId).pipe(
      takeUntil(this.ngUnsubscribe)
    ).subscribe((resp) => {
      if (resp.status == 'SUCCESS') {
        console.log("resp",resp);
        this.frontEnd = resp.data.filter((item: { type: string; }) => item.type == 'Front End');
        this.backEnd = resp.data.filter((item: { type: string; }) => item.type == 'Back End');
        this.testingTools = resp.data.filter((item: { type: string; }) => item.type == 'Testing Tools');
      }
    }, (error: any) => {
      
    });
  }

}