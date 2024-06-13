import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ConfigService } from '../authentication/config.services';
import { concatMap } from 'rxjs/operators';
import { of, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ManageResourcesService {
    private baseUrl = '/api/v1/resources';

    httpClient = inject(HttpClient);
    constructor(private configService: ConfigService) {}

    public getResources() {
      return this.httpClient
        .get(this.configService.apiUrl + this.baseUrl + "/getResources", {})
        .pipe(
          concatMap((resp: any) => {
            return resp.status === "SUCCESS"
              ? of(resp)
              : throwError(resp);
          })
        );
    }

    getTrainingList() {
      return this.httpClient.get(this.configService.apiUrl + this.baseUrl + "/getViewTrainingDtl", {});
    }
  
    viewResourceCertification(ownerId: string) {
      return this.httpClient.get(this.configService.apiUrl + `/api/v1/authentication/viewResourceCertification/${ownerId}`);
    }

}