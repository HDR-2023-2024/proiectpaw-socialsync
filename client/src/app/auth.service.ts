import { Injectable, Inject } from '@angular/core';
import { LOCAL_STORAGE, StorageService } from 'ngx-webstorage-service';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})

export class AuthService {

  constructor(@Inject(LOCAL_STORAGE) private storage: StorageService, private http: HttpClient) { }
  private loginUrl = 'http://localhost:8086/api/v1/users/login';
  private loginInfo: any | null = null;


  login(username: string, password: string): Observable<any> {
    const postData = { username: username, password: password };

    return this.http.post(this.loginUrl, postData, { observe: 'response' }).pipe(
      tap((response: HttpResponse<any>) => {
        console.log(response.body)
        this.loginInfo = response.body;
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

  logout() {
    console.log("Delogat")
    this.storage.remove("Token");
    this.storage.remove("PhotoId");
    this.storage.remove("Username");
  }

  isUserLoggedIn(): boolean {
    return this.storage.get("Token") != null;
  }

  getAvatar() {
      if (this.storage.get("PhotoId") == null) {
        return "assets/images/avatar.png"
      } else {
        return this.storage.get("PhotoId") ;
      }
  }

  getUsername() {
    return "madalinaB"
  }
}

export interface ApiResponse {
  status: number;
}