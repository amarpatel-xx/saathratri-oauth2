import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AddOn } from '../models/add-on.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AddOnService {
  private apiUrl = `${environment.apiUriAddOnService}/api/add-ons`; // Replace with actual endpoint

  constructor(private http: HttpClient) {}

  getAddOns(): Observable<AddOn[]> {
    return this.http.get<AddOn[]>(this.apiUrl);
  }
}
