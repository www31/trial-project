import { HttpClient } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";

@Injectable({
    providedIn: 'root'
  })
export class SearchService {
    _http = inject(HttpClient);

    search(searchTerm: string) {
        return this._http.get(`http://localhost:3000/api/search/${searchTerm}`);
    }
}