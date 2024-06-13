import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TrainingDetails } from '../../../models/admin/manage-resources/training-details';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ViewTrainingDetailsService {

  private apiServerUrl = '';

  constructor(private http: HttpClient) { }

  public getTrainingById(id: number): Observable<TrainingDetails> {
    return this.http.get<TrainingDetails>(
      `${this.apiServerUrl}/mng-rsrcs/training/${id}`
      );
  }

  public getTrainings() : Observable<TrainingDetails[]> {
    return this.http.get<TrainingDetails[]>(
      `${this.apiServerUrl}/mng-rsrcs/trainings`
    )
  }

}
