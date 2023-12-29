import { Observable, of } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LOCAL_STORAGE, StorageService } from 'ngx-webstorage-service';
import { HttpResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthService } from './auth.service';
import { Location } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpClient, @Inject(LOCAL_STORAGE) private storage: StorageService, private router: Router, private authService: AuthService,private location: Location) {}

  getComment(postId: string, page: string): Observable<any> {
    return this.http.get('http://localhost:8086/api/v1/query/posts/'+postId+'/comments'+ '?page=' + String(page));
  }

  private apiUrl = 'http://localhost:8086/api/v1/comments';
  postComment(postId:string, content: string): Observable<any> {
    
    const commentData = { postId, content };
    const token = this.authService.getToken(); 
    console.log(token);
    const headers = new HttpHeaders({
      'Authorization': token
    });
    if(token == undefined){
        this.router.navigate(['/unauthorized']);
      
    }

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

  deleteComm(id : string) {
    const token = this.storage.get('Token');

    const headers = new HttpHeaders({
      'Authorization': token
    });

    return this.http.delete<any>("http://localhost:8086/api/v1/comments/" + id  , { headers: headers }).subscribe(
      data => {
        //alert("Comentariu șters cu succes!");
        //window.location.reload();
      },
      error => {
        console.error('Eroare:', error);
        if (error.status == 401) {
          this.router.navigate(['/unauthorized']);
        }
      }
    );
  }

  updateComment(commentId:string, content: string): Observable<any> {
    
    const commentData = { commentId, content };
    console.log(content);
    const token = this.authService.getToken(); 
    console.log(token);
    const headers = new HttpHeaders({
      'Authorization': token
    });
    if(token == undefined){
        this.router.navigate(['/unauthorized']);
      
    }

    return this.http.put<any>("http://localhost:8086/api/v1/comments/" + commentId, commentData, { headers, observe: 'response' }).pipe(
      tap((response: HttpResponse<any>) => {
        console.log(response);
        return response;
      }),
      catchError(error => {
        return of(error);
      })
    );
  }
}
