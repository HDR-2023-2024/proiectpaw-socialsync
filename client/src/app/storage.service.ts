import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor(private http: HttpClient, private authService: AuthService) { }

  getData(elements : any): Observable<any> {
    let url = 'http://localhost:8086/api/v1/storage/get-file-names';
    let headers = new HttpHeaders();
    if (this.authService.isUserLoggedIn()) {
      console.log("ceva")
      headers = new HttpHeaders({
        'Authorization': this.authService.getToken()
      });
    }
    console.log(headers)
    return this.http.post(url,elements, { headers: headers });
  }

}
