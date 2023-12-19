import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserPostsService {
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


  async getDataSync(apiUrl: string): Promise<any> {

    let headers = new HttpHeaders();
    if (this.authService.isUserLoggedIn()) {
      console.log("ceva")
      headers = new HttpHeaders({
        'Authorization': this.authService.getToken()
      });
    }
    try {
      console.log(headers)
      const data = await this.http.get(apiUrl, { headers: headers }).toPromise();
      console.log(apiUrl);
      return data;
    } catch (error) {
      console.error('Eroare la obtinerea datelor:', error);
      throw error;
    }
  }

  getDataTopics(apiUrl: string): Observable<any> {
    return this.http.get(apiUrl);
  }


  async getDataSyncTopics(apiUrl: string): Promise<any> {
    //console.log(page)
    let headers = new HttpHeaders();
    if (this.authService.isUserLoggedIn()) {
      console.log("ceva")
      headers = new HttpHeaders({
        'Authorization': this.authService.getToken()
      });
    }
    try {
      const data = await this.http.get(apiUrl,{ headers: headers }).toPromise();
      console.log(apiUrl);
      return data;
    } catch (error) {
      console.error('Eroare la obtinerea datelor:', error);
      throw error;
    }

  }
}
