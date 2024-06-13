import { Component, OnInit, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PanelModule } from 'primeng/panel';
import { CardModule } from 'primeng/card';
import { ChartModule } from 'primeng/chart';
import { UserAddTrainingsComponent } from './user-add-training/user-add-training';
import { ManageTrainingService } from '../../../../service/manage-training.service';
import { HttpClient } from '@angular/common/http';
import { AddTrainingModel } from '../../../../models/addtrainingmodel';

@Component({
  selector: 'user-trainings',
  standalone: true,
  imports: [CommonModule, FormsModule, UserAddTrainingsComponent, PanelModule, ChartModule, CardModule],
  templateUrl: './user-trainings.component.html',
  styleUrls: ['./user-trainings.component.css']
})
export class UserTrainingsComponent implements OnInit {
  @Input() isAddTrainingVisible: boolean = false;
  
  trainingList: AddTrainingModel[] = [];
  public errMessage: string | null = null;

  constructor( private manageTrainingService: ManageTrainingService, private httpClient: HttpClient ) { }

  ngOnInit( ): void {
    this.getTrainingsbyUser();
  }

  onAddTrainingClicked() {
    this.isAddTrainingVisible = true;
  }

  onPopupClose() {
    this.isAddTrainingVisible = false;
  }

  getTrainingsbyUser() {
    console.log('Get Trainings by User - Start');
    this.manageTrainingService.getTrainingsbyUser()
      .subscribe((res: any) => {
        this.errMessage="";
        this.trainingList = res.data;
        console.log(this.trainingList, "<<<<<< RES");
      }, err => {
        this.errMessage = err.error;
        console.log(err, "<<<<< ERROR");
      });
  }
}