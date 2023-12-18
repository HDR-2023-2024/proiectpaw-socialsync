import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { LOCAL_STORAGE, StorageService } from 'ngx-webstorage-service';

@Injectable({
  providedIn: 'root'
})
export class CreatePostService {
  private apiUrl = 'http://localhost:8086/api/v1/posts';

  constructor(private http: HttpClient, @Inject(LOCAL_STORAGE) private storage: StorageService) {}

  addPost(title: string, content: string, photo:string): Observable<any> {
    const postData = { title, content, photo };
    const token = this.storage.get('Token'); 

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });

    return this.http.post<any>(this.apiUrl, postData, { headers, observe: 'response'} ).pipe(
      tap((response: HttpResponse<any>) => {
        console.log(response.body);
        this.storage.set("Token", response.body.token);
        this.storage.set("PhotoId", response.body.photoId);
        this.storage.set("Username", response.body.username);
        return response;
      }),
      catchError(error => {
        return of(error);
      })
    );
  }
}