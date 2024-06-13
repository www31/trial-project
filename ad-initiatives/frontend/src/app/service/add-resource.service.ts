import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ConfigService } from '../authentication/config.services';
import { ResourceInfosInterface } from '../views/admin/components/add-user-form/add-user-from.interface';


@Injectable({
  providedIn: 'root'
})
export class AddResourceService {
  httpClient = inject(HttpClient);
  constructor(private configService: ConfigService) {

   }

  addResource(resourceInfos: ResourceInfosInterface) {
    return this.httpClient.post(this.configService.apiUrl + "/api/v1/authentication/addResource",  resourceInfos);
  }

  addResourceCertification(formData: FormData) {
    return this.httpClient.post(this.configService.apiUrl + "/api/v1/authentication/resourceCertificationUpload",  formData);
  }

  viewResource(id: string) {
    return this.httpClient.get(this.configService.apiUrl + `/api/v1/authentication/viewResource/${id}`);
  }

  viewResourceCertification(ownerId: string) {
    return this.httpClient.get(this.configService.apiUrl + `/api/v1/authentication/viewResourceCertification/${ownerId}`);
  }

  getAllResource() {
    return this.httpClient.get(this.configService.apiUrl + '/api/v1/authentication/getResources');
  }

  editResource(id: number, resourceInfos: any) {
    return this.httpClient.put(this.configService.apiUrl + `/api/v1/authentication/editResource/${id}`,  resourceInfos);
  }

  getSkills() {
    return this.httpClient.get(this.configService.apiUrl + '/api/v1/authentication/getSkills');
  }

  editResourceCertification(formData: FormData) {
    return this.httpClient.put(this.configService.apiUrl + "/api/v1/authentication/resourceCertificationUpdate",  formData);
  }
}