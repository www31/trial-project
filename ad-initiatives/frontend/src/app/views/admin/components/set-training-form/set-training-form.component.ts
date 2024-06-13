import { SetTrainingService } from './../../../../service/set-training.services';
import { CommonModule } from '@angular/common';
import { CUSTOM_ELEMENTS_SCHEMA, Component, EventEmitter, Input, Output, SimpleChanges, ViewEncapsulation } from '@angular/core';
import { CustomBottonComponent } from '../../../../shared/components/custom-button/custom-button.component';
import { MultiSelectModule } from 'primeng/multiselect';
import { CalendarComponent } from '../../../../shared/components/calendar/calendar.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DropdownInterface, SelectItemGroupInterface } from '../add-user-form/add-user-from.interface';
import { DropdownModule } from 'primeng/dropdown';
import { AddResourceService } from '../../../../service/add-resource.service';
import { FormFieldInterface } from './set-training.interface';
import { CustomCalendarComponent } from '../../../../shared/components/custom-calendar/custom-calendar.component';
import { DialogBoxComponent } from '../../../../shared/components/dialog-box/dialog-box.component';

@Component({
  selector: 'app-set-training-form',
  standalone: true,
  imports: [
            CommonModule,
            CustomBottonComponent, 
            MultiSelectModule, 
            FormsModule,
            ReactiveFormsModule,
            CalendarComponent,
            DropdownModule,
            CustomCalendarComponent,
            CustomBottonComponent,
            DialogBoxComponent
           ],
  templateUrl: './set-training-form.component.html',
  styleUrl: './set-training-form.component.css',
  encapsulation:ViewEncapsulation.None
})
export class SetTrainingFormComponent {
  @Input() isSetTrainingOpen: boolean = false;
  @Input() actionTypeForSetTraining: string = '';
  @Output() onCloseClick = new EventEmitter<void>();
  @Output() isSetTrainingOpenChange = new EventEmitter<boolean>();
  categoryList!:SelectItemGroupInterface[];
  subCategoryList!: any;
  teamList!: SelectItemGroupInterface[];
  odc!: SelectItemGroupInterface[];
  locations!: SelectItemGroupInterface[];
  careerLevels!: SelectItemGroupInterface[];
  skillList!: SelectItemGroupInterface[];
  selectedSkillOption!: DropdownInterface[];
  categoryOptions!: any;
  selectedCategory: string = '';
  subCategoryOptions: string ='';
  subCategoryLabel: string = '';
  formFields: FormFieldInterface[] = [];
  isUserListModalOpen : boolean = false;
  resourceList: any[] = [];
  toggleSelectItemsUser: any[]=[];
  selectedUsers: any[]=[];
  filterQuery: string = '';
  isThereTrainingsSelected: boolean = false;
  isAddTrainingSuccess: boolean = false;

  ngOnInit(): void { 
    this.categoryOptions = 'odc';
    this.onSingleSelectChange(this.categoryOptions);
    this.getSkills();
    this.getResources();
  }


  constructor(private addResourceService: AddResourceService, private setTrainingService: SetTrainingService){
    this.categoryList = [
      {
        label: 'ODC',
        value: 'odc',
      },
      {
        label: 'Region/Location',
        value: 'region_location',
      },
      {
        label: 'Team/Projects',
        value: 'team_projects'
      },
      {
        label: 'Career levels',
        value: 'career_levels'
      },
      {
        label: 'Users',
        value: 'users'
      },
    ];

      this.odc = [
        {
            label: 'PH ODC',
            value: 'ph_odc',
        },
        {
            label: 'HK ODC',
            value: 'hk_odc',
        },
     ];

     this.locations = [
      {
          label: 'Philippines',
          value: 'philippines',
      },
      {
          label: 'Hongkong',
          value: 'hongkong',
      },
   ];

    this.careerLevels = [
      {
          label: 'I03',
          value: 'i03',
      },
      {
          label: 'I04',
          value: 'i04',
      },
      {
          label: 'I05',
          value: 'i05',
      },
    ];

    this.teamList = [
      {
          label: 'Ad Public',
          value: 'ad_public',
      },
      {
          label: 'EMPF',
          value: 'empf',
      },
      {
          label: 'Ad Initiative',
          value: 'ad_initiative'
      }
    ];

    this.skillList = [
      {
          label: '',
          value: '',
      }
  ];

    this.subCategoryList = {
      odc: this.odc,
      loc: this.locations,
      careerLevels: this.careerLevels,
      users: 'users'
    }
  }

  preventBackdropClick(event: MouseEvent) {
    event.stopPropagation();
  }

  getResources(){
    var cert = [];
    this.addResourceService.getAllResource()
    .subscribe((res: any) => {
      this.resourceList=res
      console.log(res, "<<<<<< RES")
    }, err => {
      console.log(err, "<<<<< ERROR")
    });
  }

  onClosed() {
    this.isSetTrainingOpen = false;
    this.isSetTrainingOpenChange.emit(this.isSetTrainingOpen)
    this.reset();
  }

  onClosedUserListModal() {
    this.isUserListModalOpen = false;
  }

  onSingleSelectChange(event:any) {
    console.log(event, "<<<< selected")
    this.selectedCategory = event;


    switch (event) {
      case 'odc':
        this.subCategoryOptions = this.odc[0].value;
        this.subCategoryLabel = 'ODC'
        this.isUserListModalOpen = false;
        break;
      case 'region_location':
        this.subCategoryOptions = this.locations[0].value;
        this.subCategoryLabel = 'Location'
        this.isUserListModalOpen = false;
        break;
      case 'team_projects':
        this.subCategoryLabel = 'Team/Projects'
        this.subCategoryOptions = this.teamList[0].value;
        this.isUserListModalOpen = false;
        break;
      case 'career_levels':
        this.subCategoryLabel = 'Career Level'
        this.subCategoryOptions = this.careerLevels[0].value;
        this.isUserListModalOpen = false;
        break;
      case 'users':
        this.subCategoryLabel = 'users'
        this.isUserListModalOpen = true;
        break;
      default:
        this.subCategoryLabel = ''
        this.subCategoryOptions = '';
        this.isUserListModalOpen = false;
    }

    console.log(this.isUserListModalOpen, "this.isUserListModalOpen")
  }

  getSubCategory() {
    switch (this.selectedCategory) {
      case 'odc':
        return this.odc;
      case 'region_location':
        return this.locations;
      case 'team_projects':
        return this.teamList;
      case 'career_levels':
        return this.careerLevels;
      case 'users':
        return [];
      default:
        return this.odc; 
    }
  }

  getSkills() {
    this.addResourceService.getSkills()
    .subscribe((res: any) => {
      console.log(res, "<<<<<< SKILLS")
      if(res) {
        res.map(({skillId, skillName} :any ) => (
          this.skillList.push({
            value: skillId ? skillId : '',
            label: skillName ? skillName : '',
          })
        ))
      }
    }, err => {
      this.skillList=[{
        value: '',
        label: ''
      }]
      console.log(err, "<<<<< ERROR")
    });
  }

  onMultiSelectChange(selected: string[]) {
    console.log(selected, "selected")
    this.formFields = selected.map(value => {
      const existingField = this.formFields.find(field => field.value === value);
      const skill: any = this.skillList.find(skill => skill.value === value);
      return existingField ? existingField : { label: skill.label, value, date: null };
    });
  }

  selectedDate(event: { date: Date, context?: any }) {
    console.log(event.date, "<<<<<< date");
    if (event.context) {
      const field = this.formFields.find(f => f.value === event.context.value);
      if (field) {
        field.date = event.date;
      }
    }
  }

  reset() {
    this.selectedUsers = [];
    this.toggleSelectItemsUser = [];
    this.selectedSkillOption = [];
    this.formFields = [];
    this.categoryOptions = 'odc';
    this.onSingleSelectChange(this.categoryOptions);
  }

  validate() {
    return this.formFields.length === 0;
  }

  applyTrainings() {
    console.log(this.formFields, "<<<<<< formFields");
    console.log(this.selectedUsers, " selectedUsers")
    
    const memberIds = this.selectedUsers.map(user => user.memberId);
    const trainings = this.formFields.map(formField => {
      return {
          label: formField.label,
          value: formField.value,
          date: formField.date
        };
      });

    let requestData = {
      memberIds: memberIds,
      trainings: trainings
    };

    if (this.validate()) {
      this.isThereTrainingsSelected = true
    } else {
      this.isThereTrainingsSelected = false;
    }

    if (this.categoryOptions === 'users' && !this.validate()) {
      console.log("??????")
        this.setTrainingService.setTrainingForSelectedUser(requestData)
        .subscribe((res: any) => {
          console.log(res, "<<<<<< RES")
          this.isAddTrainingSuccess = true;
        }, err => {
          console.log(err, "<<<<< ERROR")
        });
    } else if ((this.categoryOptions === 'region_location' || this.categoryOptions === 'odc') && !this.validate()) {
        const _resourceList = this.resourceList.filter(user => user.region?.trim().toUpperCase() === this.subCategoryOptions.toUpperCase());
        const _memberIds = _resourceList.map(user => user.memberId);
        requestData.memberIds = _memberIds;

        this.setTrainingService.setTrainingForSelectedUser(requestData)
        .subscribe((res: any) => {
          this.isAddTrainingSuccess = true;
          console.log(res, "<<<<<< RES")
        }, err => {
          console.log(err, "<<<<< ERROR")
        });
    } else if (this.categoryOptions === 'team_projects' && !this.validate()) {
        const _resourceList = this.resourceList.filter(user => {
            const teams = user.team?.split(',');
            return teams.some((team: string) => team.trim().toUpperCase() === this.subCategoryOptions.toUpperCase());
          });

        const _memberIds = _resourceList.map(user => user.memberId);
        requestData.memberIds = _memberIds;

        this.setTrainingService.setTrainingForSelectedUser(requestData)
        .subscribe((res: any) => {
          this.isAddTrainingSuccess = true;
          console.log(res, "<<<<<< RES")
        }, err => {
          console.log(err, "<<<<< ERROR")
        });
    } else if (this.categoryOptions === 'career_levels' && !this.validate()) {
        const _resourceList = this.resourceList.filter(user => user.careerStep.toUpperCase().trim() === this.subCategoryOptions.toUpperCase());
        const _memberIds = _resourceList.map(user => user.memberId);
        requestData.memberIds = _memberIds;

        this.setTrainingService.setTrainingForSelectedUser(requestData)
        .subscribe((res: any) => {
          this.isAddTrainingSuccess = true;
          console.log(res, "<<<<<< RES")
        }, err => {
          console.log(err, "<<<<< ERROR")
        });
    }

    console.log(requestData, "<<<<requestData")
  }

  selectUser() {
    console.log(this.toggleSelectItemsUser, "toggleSelectItemsUser")
    this.selectedUsers = this.toggleSelectItemsUser
    this.isUserListModalOpen = false;
    console.log(this.selectedUsers, "selectedUsers")
  }

  toggleSelectionUser(selected: any) {
    console.log(selected, "selected")
    const userIndex = this.toggleSelectItemsUser.findIndex(user => user.memberId === selected.memberId);

    if (userIndex !== -1) {
      this.toggleSelectItemsUser.splice(userIndex, 1);
    } else {
      this.toggleSelectItemsUser.push(selected);
    }
    console.log(this.toggleSelectItemsUser, "toggleSelectItemsUser")
  }

  isSelected(user: any): boolean {
    return this.toggleSelectItemsUser.some(selectedUser => selectedUser.memberId === user.memberId);
  }

  filterUsers(): any {
    let filteredUsers = this.resourceList;

    if (this.filterQuery) {
      const lowerCaseQuery = this.filterQuery.toLowerCase().trim();
      filteredUsers = filteredUsers.filter(user =>
        user.firstname.toLowerCase().includes(lowerCaseQuery) ||
        user.lastname.toLowerCase().includes(lowerCaseQuery) ||
        (user.middlename && user.middlename.toLowerCase().includes(lowerCaseQuery))
      );
    }

    if (this.subCategoryOptions) {
      filteredUsers = filteredUsers.filter(user =>
        user.region === this.subCategoryOptions?.toUpperCase()
      );
    }

    return filteredUsers;
  }

  isButtonDisabled(): boolean {
    return this.toggleSelectItemsUser.length === 0;
  }

  removeItem(user: any) {
    const userIndex = this.selectedUsers.findIndex(selectedUser => selectedUser.memberId === user.memberId);

    if (userIndex !== -1) {
      this.selectedUsers.splice(userIndex, 1);
    }
  }

  requiredFieldDialogMsg() {
    return 'Please selected trainings'
  }

  closeDialog(type?:string) {
    this.isThereTrainingsSelected = false;

    if (type === 'success') {
      this.isAddTrainingSuccess = false;
      this.onClosed();
    }
  }
}
