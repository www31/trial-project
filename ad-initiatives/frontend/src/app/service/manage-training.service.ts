import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ConfigService } from '../authentication/config.services';
import { AddTrainingModel } from '../models/addtrainingmodel';
import { TrainingLinksModel } from '../models/training-links-model';
import { SkillDetail } from '../models/skills.model';

@Injectable({
  providedIn: 'root'
})
export class ManageTrainingService {
    private baseUrl = '/api/v1/trainings'; // Replace with your actual backend API URL

  
    httpClient = inject(HttpClient);
    constructor(private configService: ConfigService) {}

    getTrainingList(){
        return this.httpClient.get(this.configService.apiUrl + this.baseUrl + "/getTrainingList", {  });
    }

    deleteTraining(id: string){
        return this.httpClient.delete(this.configService.apiUrl + this.baseUrl + "/deleteTraining/" + id);
    }

    addTraining(trainingDetails: AddTrainingModel) {
        return this.httpClient.post(this.configService.apiUrl + this.baseUrl + "/addTraining", trainingDetails);
    }

    getTrainingLinks(id: string): Observable<TrainingLinksModel[]> {
      return this.httpClient.get<TrainingLinksModel[]>(`${this.configService.apiUrl}${this.baseUrl}/getTrainingLinks/${id}`);
    }

    getCalendarSchedule(){
      return this.httpClient.get(this.configService.apiUrl + this.baseUrl + "/getViewCalendarSchedule", {  });
    }

    getTrainingsbyUser(){
      return this.httpClient.get(this.configService.apiUrl + this.baseUrl + "/getTrainingsbyUser", {  });
    }
    
    searchTrainingbyCriteria(criteria: any): Observable<{ data: AddTrainingModel[] }> {
      let params = new HttpParams();
    
      for (let key in criteria) {
        if (criteria[key]) { 
          params = params.append(key, criteria[key]);
        }
      }
    
      return this.httpClient.get<{ data: AddTrainingModel[] }>(`${this.configService.apiUrl}${this.baseUrl}/searchTraining`, { params });
    }

    registerUserTraining(userTrainings: SkillDetail) {
      return this.httpClient.post(this.configService.apiUrl + this.baseUrl + "/registerUserTraining", userTrainings);
  }

}