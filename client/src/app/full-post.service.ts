import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FullPostService {

  constructor(private http: HttpClient) {}

  getData(postId: string, page: string): Observable<any> {
    return this.http.get('http://localhost:8086/api/v1/query/posts/'+postId+'?page=' + String(page));
  }
}