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

  login(username: string, password: string): Observable<any> {
    const postData = { username: username, password: password };

    return this.http.post(this.loginUrl, postData, { observe: 'response' }).pipe(
      tap((response: HttpResponse<any>) => {
        console.log(response.body)
        this.storage.set("Token",response.body.token);
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
  }

  isUserLoggedIn(): boolean {
    return this.storage.get("Token") != null;
  }

  getAvatar() {
    return "assets/images/avatar.png"
  }

  getUsername() {
    return "madalinaB"
  }
}

export interface ApiResponse {
  status: number;
}