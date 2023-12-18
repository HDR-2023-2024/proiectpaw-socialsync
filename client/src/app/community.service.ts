import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CommunityService {

  constructor(private http: HttpClient) { }

  getData(page: string): Observable<any> {
    return this.http.get('http://localhost:8086/api/v1/query/topics?page=' + String(page));
  }

  getCommunityById(postId:string, page: string): Observable<any> {
    return this.http.get('http://localhost:8086/api/v1/query/topics/'+postId+'?page=' + String(page));
  }

  getCommunityPostsById(postId:string, page: string): Observable<any> {
    return this.http.get('http://localhost:8086/api/v1/query/topics/'+postId+'/posts?page=' + String(page));
  }

}
