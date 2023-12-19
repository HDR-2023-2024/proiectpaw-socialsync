import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HttpHeaders } from '@angular/common/http';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class FullPostService {

  constructor(private http: HttpClient, private authService: AuthService) {}

  getData(postId: string, page: string): Observable<any> {
    let headers = new HttpHeaders();
    if (this.authService.isUserLoggedIn()) {
      console.log("ceva")
      headers = new HttpHeaders({
        'Authorization': this.authService.getToken()
      });
    }
    return this.http.get('http://localhost:8086/api/v1/query/posts/'+postId+'?page=' + String(page), { headers: headers });
  }
}