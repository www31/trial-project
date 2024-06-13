import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ConfigService } from '../authentication/config.services';

@Injectable({
  providedIn: 'root'
})
export class ManageSkillsService {
    private baseUrl = '/api/v1/skills'; // Replace with your actual backend API URL

    httpClient = inject(HttpClient);
    constructor(private configService: ConfigService) {}

    getSkillDetails(){
        return this.httpClient.get(this.configService.apiUrl + this.baseUrl + "/getSkillDetails", {  });
    }

}