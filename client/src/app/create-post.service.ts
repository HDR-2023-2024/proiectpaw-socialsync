import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
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
        this.router.navigate(['/full-post', data.id]);
      },
      error => {
        console.error('Eroare:', error);
        if (error.status == 401) {
          this.router.navigate(['/unauthorized']);
        }
      }
    );
  }

  deletePost(id : string) {
    const token = this.storage.get('Token');

    const headers = new HttpHeaders({
      'Authorization': token
    });

    return this.http.delete<any>("http://localhost:8086/api/v1/posts/" + id  , { headers: headers }).subscribe(
      data => {
        alert("Postarea a fost ștearsă cu succes!");
        this.router.navigate(['/home']);
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