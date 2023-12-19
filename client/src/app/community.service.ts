import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HttpHeaders } from '@angular/common/http';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class CommunityService {

  constructor(private http: HttpClient, private authService: AuthService) { }

  getData(page: string): Observable<any> {
    let headers = new HttpHeaders();
    if (this.authService.isUserLoggedIn()) {
      console.log("ceva")
      headers = new HttpHeaders({
        'Authorization': this.authService.getToken()
      });
    }
    return this.http.get('http://localhost:8086/api/v1/query/topics?page=' + String(page),{ headers: headers });
  }

  getCommunityById(postId:string, page: string): Observable<any> {
    let headers = new HttpHeaders();
    if (this.authService.isUserLoggedIn()) {
      console.log("ceva")
      headers = new HttpHeaders({
        'Authorization': this.authService.getToken()
      });
    }
    return this.http.get('http://localhost:8086/api/v1/query/topics/'+postId+'?page=' + String(page),{ headers: headers });
  }

  getCommunityPostsById(postId:string, page: string): Observable<any> {
    let headers = new HttpHeaders();
    if (this.authService.isUserLoggedIn()) {
      console.log("ceva")
      headers = new HttpHeaders({
        'Authorization': this.authService.getToken()
      });
    }
    return this.http.get('http://localhost:8086/api/v1/query/topics/'+postId+'/posts?page=' + String(page),{ headers: headers });
  }

}
