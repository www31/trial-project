import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ConfigService } from '../authentication/config.services';

@Injectable({
  providedIn: 'root'
})
export class ResourceDataService {
    private baseUrl = '/api/v1/reports'; // Replace with your actual backend API URL

  
    httpClient = inject(HttpClient);
    constructor(private configService: ConfigService) {}

    getRecordsList(){
        return this.httpClient.get(this.configService.apiUrl + this.baseUrl + "/getRecordsList", {  });
    }

  }