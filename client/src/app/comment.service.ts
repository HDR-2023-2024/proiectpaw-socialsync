import { Observable, of } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LOCAL_STORAGE, StorageService } from 'ngx-webstorage-service';
import { HttpResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpClient, @Inject(LOCAL_STORAGE) private storage: StorageService) {}

  getComment(postId: string, page: string): Observable<any> {
    return this.http.get('http://localhost:8086/api/v1/query/posts/'+postId+'/comments'+ '?page=' + String(page));
  }

  private apiUrl = 'http://localhost:8086/api/v1/comments';
  postComment(postId:string, content: string): Observable<any> {
    
    const commentData = { postId, content };
    const token = this.storage.get('Token'); 
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });

    return this.http.post<any>(this.apiUrl, commentData, { headers, observe: 'response' }).pipe(
      tap((response: HttpResponse<any>) => {
        console.log(response.body);
        return response;
      }),
      catchError(error => {
        return of(error);
      })
    );
  }
}