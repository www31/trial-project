import { Component, OnInit, Input, Output, EventEmitter, numberAttribute, NO_ERRORS_SCHEMA, CUSTOM_ELEMENTS_SCHEMA, ChangeDetectorRef} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule} from '@angular/forms';
import { TableComponent } from '../../../../shared/components/table/table.component';
import { AddTrainingModel } from '../../../../models/addtrainingmodel';
import { TrainingLinksModel } from '../../../../models/training-links-model';
import { HttpClient } from '@angular/common/http';
import { PopupComponent } from './add-more-training-popup';
import { ManageTrainingsComponent } from '../manage-trainings/manage-trainings.component';
import { DialogBoxComponent } from '../../../../shared/components/dialog-box/dialog-box.component';
import { ManageTrainingService } from '../../../../service/manage-training.service';
import { UserInfoService } from '../../../../service/user-info.service';
import { MultiSelectModule } from 'primeng/multiselect';

@Component({
  selector: 'add-edit-training-popup',
  standalone: true,
  imports: [FormsModule, CommonModule, TableComponent, PopupComponent, ManageTrainingsComponent, DialogBoxComponent, MultiSelectModule],
  templateUrl: './add-edit-training.html',
  styleUrl: './add-edit-training.css',
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class AddEditTrainingComponent implements OnInit{
  @Input() isPopupVisible: boolean = false;
  @Input() isAddMorePopupVisible: boolean = false;
  @Input() trainingDetails!: AddTrainingModel;
  @Output() saveTraining = new EventEmitter<AddTrainingModel>();
  @Output() close = new EventEmitter<void>();
  @Output() closeAddMore = new EventEmitter<void>();
  @Output() trainingLinksListsChange = new EventEmitter<TrainingLinksModel[]>();
  @Output() addMoreClicked = new EventEmitter<string>();

  isOpen: boolean = false;
  isSuccess: boolean = false;
  isError: boolean = false;
  trainingLinksLists: TrainingLinksModel[] = [];
  public errMessage: string | null = null;
  maxDisplay: number = 4;

  @Input() rolesList: [] = []; 
  @Output() selectedRolesChange = new EventEmitter<string[]>(); 

  selectedRoles: string[] = [];
  info = { placeholder: 'Select Roles', field: 'roles' }; 

  constructor(private manageTrainingService: ManageTrainingService, 
              private userInfoService: UserInfoService,
              private httpClient: HttpClient,
              private cdr: ChangeDetectorRef) { 
  }

  ngOnInit() {
    this.getUserRoles();
  }

  showPopup() {
    this.isPopupVisible = true;
    this.close.emit();
  }

  closePopup() {
    this.isPopupVisible = false;
    this.close.emit();
  }

  initializeTrainingDetails() {
    this.trainingDetails = new AddTrainingModel();
  }

  onSaveClick() {
    const roleNames = this.selectedRoles.map((role: any) => role.roleName);
    this.trainingDetails.trainingTags = roleNames.join(', ');
    console.log("Training details before save:", this.trainingDetails);
    this.isOpen = true;
}

  onCloseClick(){
    this.isOpen = false;
    this.isSuccess = false;
    this.isError = false;
  }

  proceedSave() {
    if (!this.isValidTraining(this.trainingDetails)) {
        console.log('Please fill in the required fields.');
        this.isError = true;
        return;
    } else {
        console.log('Training details before adding', this.trainingDetails);
        console.log('Training details are valid. Adding training...');
        this.addTraining(this.trainingDetails);
        this.isSuccess = true;
    }
  }

  addTraining(trainingDetails: AddTrainingModel) {
    this.manageTrainingService.addTraining(this.trainingDetails)
      .subscribe(
        (response) => {
          console.log('Training added successfully:', response);
          this.closePopup();
          this.initializeTrainingDetails();
        },
        (error) => {
          console.error('Failed to add training:', error);
        }
      );
  }

  isValidTraining(trainingDetails: AddTrainingModel): boolean {
    return (
      trainingDetails.trainingName?.trim() !== '' &&
      trainingDetails.trainingType?.trim() !== '' &&
      trainingDetails.productName?.trim() !== '' &&
      trainingDetails.startDate !== undefined && 
      trainingDetails.dueDate !== undefined && 
      this.isValidDate(trainingDetails.startDate) && 
      this.isValidDate(trainingDetails.dueDate) &&
      trainingDetails.description?.trim() !== '' 
    );
  }

  private isValidDate(dateString: any): boolean {
    const date = new Date(dateString);
    return !isNaN(date.getTime());
  }

  onaddMoreClicked() {
    this.trainingDetails.trainingId = this.trainingDetails.id;
    this.isAddMorePopupVisible = true;
    if (this.trainingDetails.id != null) {
      console.log ("Current Training ID:", this.trainingDetails.id);
      this.addMoreClicked.emit(this.trainingDetails.id);
    }
  }

  closeAddMorePopup(): void {
    this.isAddMorePopupVisible = false;
  }

  getUserRoles() {
    this.userInfoService.getUserRoles().subscribe(
      (res: any) => {
        this.rolesList = res.data;
        let selRoles = this.trainingDetails.trainingTags
          ? this.trainingDetails.trainingTags.split(',').map(tag => tag.trim())
          : [];
        this.selectedRoles = this.rolesList.filter(data => {
          console.log(data);
          if(selRoles.includes(data['roleName'])) return true;
          return false;
        });
        /*this.selectedRoles = this.trainingDetails.trainingTags
          ? this.trainingDetails.trainingTags.split(',').map(tag => tag.trim())
          : [];*/
  
        console.log('Tags:', this.trainingDetails.trainingTags);
        console.log('Roles List:', this.rolesList);
        console.log('Selected Roles:', this.selectedRoles);
  
        this.cdr.detectChanges();
      },
      err => {
        this.errMessage = err.error;
        console.log('Error Retrieving User Roles List!', err);
      }
    );
  }


  onRoleSelectionChange(event: any) {
    console.log("Selected roles:", event.value);
    this.selectedRoles = event.value.map((role: any) => role.name);
    console.log("Selected role names:", this.selectedRoles);
  }

  onMultiSelectChange(field: string, event: any) {
    this.selectedRolesChange.emit(this.selectedRoles);
  }

  toggleSelection(event: { originalEvent: Event, value: any }) {
    const selectedRole = event.value;
    const index = this.selectedRoles.indexOf(selectedRole);
    if (index > -1) {
      this.selectedRoles.splice(index, 1);
    } else {
      this.selectedRoles.push(selectedRole);
    }
  }

}