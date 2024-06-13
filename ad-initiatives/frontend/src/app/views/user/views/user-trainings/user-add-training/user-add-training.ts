import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserTrainingsComponent } from '../user-trainings.component';
import { AddTrainingModel } from '../../../../../models/addtrainingmodel';
import { ManageTrainingService } from '../../../../../service/manage-training.service';
import { HttpClient } from '@angular/common/http';
import { SkillDetail } from '../../../../../models/skills.model';
import { ManageSkillsService } from '../../../../../service/manage-skills-service';

@Component({
  selector: 'user-add-training-popup',
  standalone: true,
  imports: [CommonModule, FormsModule, UserTrainingsComponent],
  templateUrl: './user-add-training.html',
  styleUrls: ['./user-add-training.css']
})
export class UserAddTrainingsComponent implements OnInit {
  @Output() close = new EventEmitter<void>();
  @Input() trainingDetails!: AddTrainingModel;
  @Input() isAddTrainingVisible: boolean = false;

  userTraining: SkillDetail = new SkillDetail;
  trainingList: AddTrainingModel[] = [];
  skillsList: any[] = [];
  public errMessage: string | null = null;

  searchCriteria: {
    trainingName: string;
    skillName: string;
    startDate: Date | null;
    endDate: Date | null;
  } = {
    trainingName: '',
    skillName: '',
    startDate: null,
    endDate: null
  };

  constructor(private manageTrainingService: ManageTrainingService, 
              private manageSkillsService: ManageSkillsService, 
              private httpClient: HttpClient) { }

  ngOnInit(): void { 
    this.getSkills();
  }

  closePopup(): void {
    this.isAddTrainingVisible = false;
    this.close.emit();
  }

  Search(): void {
    console.log("Search Criteria: ", this.searchCriteria);
    this.searchTraining(this.searchCriteria);
  }

  searchTraining(searchCriteria: { trainingName: string; skillName: string; startDate: Date | null; endDate: Date | null; }) {
    this.manageTrainingService.searchTrainingbyCriteria(searchCriteria)
      .subscribe(
        (response: { data: AddTrainingModel[] }) => {
          console.log('API Response:', response);
          if (response && response.data) {
            this.trainingList = response.data;
            console.log('Training List:', this.trainingList);
          } else {
            console.error('Response data is undefined');
          }
        },
        (error) => {
          console.error('Failed to search training:', error);
        }
      );
  }

  register(training: AddTrainingModel): void {
    console.log("Register button has been clicked for training:", training);
    this.getUserTraining(training);
    this.saveUserTraining(this.userTraining);
  }

  getUserTraining(training: AddTrainingModel){
    this.userTraining.trainingId = training.trainingId;
    this.userTraining.skillId = training.skillId;
  }

  saveUserTraining(userTraining: SkillDetail) {
    this.manageTrainingService.registerUserTraining(userTraining)
      .subscribe(
        (response) => {
          console.log('User Training added successfully:', response);
          this.closePopup();
        },
        (error) => {
          console.error('Failed to add training:', error);
        }
      );
  }

  getSkills() {
    this.manageSkillsService.getSkillDetails()
    .subscribe((res: any) => {
      this.skillsList = res.data;
      console.log('Skills List', this.skillsList)
    }, err => {
      this.errMessage = err.error;
      console.log('Error Retrieving Skills List!', err)
    });
  }

}
