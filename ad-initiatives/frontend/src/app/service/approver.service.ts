import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ConfigService } from '../authentication/config.services';
import { SkillDetail} from '../models/skills.model';
import { concatMap } from 'rxjs/operators';
import { of, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApproverService {
    private baseUrl = '/api/v1/approver';

    httpClient = inject(HttpClient);
    constructor(private configService: ConfigService) {}

    public getTeamMemberList(v_userId: string) {
      return this.httpClient
        .post(this.configService.apiUrl + this.baseUrl + "/getTeamMemberList", v_userId)
        .pipe(
          concatMap((resp: any) => {
            return resp.status === "SUCCESS"
              ? of(resp)
              : throwError(resp);
          })
        );
    }

    public getMemberCertification(v_memberId: string) {
      return this.httpClient
        .post(this.configService.apiUrl + this.baseUrl + "/getMemberCertification", v_memberId)
        .pipe(
          concatMap((resp: any) => {
            return resp.status === "SUCCESS"
              ? of(resp)
              : throwError(resp);
          })
        );
    }

    public getTrainingsForApproval(v_memberId: string) {
      return this.httpClient
        .post(this.configService.apiUrl + this.baseUrl + "/getTrainingsForApproval", v_memberId)
        .pipe(
          concatMap((resp: any) => {
            return resp.status === "SUCCESS"
              ? of(resp)
              : throwError(resp);
          })
        );
    }

    public getMemberTrainingList(v_memberId: string) {
      return this.httpClient
        .post(this.configService.apiUrl + this.baseUrl + "/getMemberTrainingList", v_memberId)
        .pipe(
          concatMap((resp: any) => {
            return resp.status === "SUCCESS"
              ? of(resp)
              : throwError(resp);
          })
        );
    }

    public getCertTrackingDtl() {
      return this.httpClient
        .get(this.configService.apiUrl + this.baseUrl + "/getCertTrackingDtl", {})
        .pipe(
          concatMap((resp: any) => {
            return resp.status === "SUCCESS"
              ? of(resp)
              : throwError(resp);
          })
        );
    }

    public getCertTrackList() {
      return this.httpClient.get(this.configService.apiUrl + this.baseUrl + "/getCertTrackingDtl", {});
    }

    public getSkills() {
      return this.httpClient.get(this.configService.apiUrl + this.baseUrl + "/getSkillName", {});
    }

    public getStatus() {
      return this.httpClient.get(this.configService.apiUrl + this.baseUrl + "/getStatus", {});
    }

}