import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class NotificationsServiceService {

  constructor(private http: HttpClient, private authService: AuthService) { }

  getData(apiUrl: string): Observable<any> {

    let headers = new HttpHeaders();
    if (this.authService.isUserLoggedIn()) {
      console.log("ceva")
      headers = new HttpHeaders({
        'Authorization': this.authService.getToken()
      });
    }
    console.log(headers)
    return this.http.get(apiUrl, { headers: headers });
  }
}
