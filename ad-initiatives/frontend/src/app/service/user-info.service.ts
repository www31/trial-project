import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ConfigService } from '../authentication/config.services';

@Injectable({
  providedIn: 'root'
})
export class UserInfoService {
    private baseUrl = '/api/v1/users'; // Replace with your actual backend API URL

  
    httpClient = inject(HttpClient);
    constructor(private configService: ConfigService) {}

    getProfileInformation(id: string){
        return this.httpClient.get(this.configService.apiUrl + this.baseUrl + "/get-profile-info/"+ id);
    }

    getUserRoles(){
      return this.httpClient.get(this.configService.apiUrl + this.baseUrl + "/getUserRoles", {  });
    }

}