
import { Injectable, Inject } from '@angular/core';
import { LOCAL_STORAGE, StorageService } from 'ngx-webstorage-service';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CreatAccountService {

  constructor(@Inject(LOCAL_STORAGE) private storage: StorageService, private http: HttpClient) { }
  private loginUrl = 'http://localhost:8086/api/v1/users';

  saveUser(username: string,email:string, password: string,photoId: string | null,gender : string,description:string): Observable<any> {
    const postData = { username: username, email: email,password : password, role : "user",photoId: photoId, gender : gender,description:description };

    return this.http.post(this.loginUrl, postData, { observe: 'response' }).pipe(
      tap((response: HttpResponse<any>) => {
        console.log(response.body)
        this.storage.set("Token", response.body.token);
        this.storage.set("PhotoId", response.body.photoId);
        this.storage.set("Username", response.body.username);
        this.storage.set("Id", response.body.id);
        return response;
      }),
      catchError(error => {
        return of(error);
      })
    );
  }
}
