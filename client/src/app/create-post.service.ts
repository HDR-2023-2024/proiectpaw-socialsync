import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { LOCAL_STORAGE, StorageService } from 'ngx-webstorage-service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class CreatePostService {
  private apiUrl = 'http://localhost:8086/api/v1/posts';

  constructor(private http: HttpClient, @Inject(LOCAL_STORAGE) private storage: StorageService, private router: Router) { }

  addPost(post: any) {
    const token = this.storage.get('Token');

    const headers = new HttpHeaders({
      'Authorization': token
    });

    return this.http.post<any>(this.apiUrl, post, { headers: headers }).subscribe(
      data => {
        console.log(data);
      },
      error => {
        console.error('Eroare:', error);
        if (error.status == 401) {
          this.router.navigate(['/unauthorized']);
        }
      }
    );
  }
}