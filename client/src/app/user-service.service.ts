import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HttpHeaders } from '@angular/common/http';
import { AuthService } from './auth.service';


@Injectable({
  providedIn: 'root'
})
export class UserServiceService {

  constructor(private http: HttpClient, private authService: AuthService) {}

  getData(id : string): Observable<any> {
    return this.http.get('http://localhost:8086/api/v1/query/users/'+id);
  }
}
