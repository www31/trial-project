import { HttpClient } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { ConfigService } from "../authentication/config.services";

@Injectable({
    providedIn: 'root'
})

export class SetTrainingService {
    httpClient = inject(HttpClient);
    constructor(private configService: ConfigService) {
        
    }

    setTrainingForSelectedUser(requestData: any) {
        return this.httpClient.post(this.configService.apiUrl + "/api/v1/authentication/setTraining",  requestData);
    }

    countUserTrainings(memberId: string) {
        return this.httpClient.get(this.configService.apiUrl + `/api/v1/authentication/countTrainings/${memberId}`);
    }
}
