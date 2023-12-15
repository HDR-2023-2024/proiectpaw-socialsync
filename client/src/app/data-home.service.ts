import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataHomeService {

  constructor(private http: HttpClient) {}

  private apiUrl = 'http://localhost:8086/api/v1/query/posts?page=0'; 
  

  getData(): Observable<any> {
    return this.http.get(this.apiUrl);
  }
}
