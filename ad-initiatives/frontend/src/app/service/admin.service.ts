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
export class AdminService {
    private baseUrl = '/api/v1/admin';

    httpClient = inject(HttpClient);
    constructor(private configService: ConfigService) {}

    public getMemberSkillSet(v_memberId: string) {
      return this.httpClient
        .post(this.configService.apiUrl + this.baseUrl + "/getMemberSkillSet", v_memberId)
        .pipe(
          concatMap((resp: any) => {
            return resp.status === "SUCCESS"
              ? of(resp)
              : throwError(resp);
          })
        );
    }

}