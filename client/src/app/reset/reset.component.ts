import { Component } from '@angular/core';
import { LOCAL_STORAGE, StorageService } from 'ngx-webstorage-service';
import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-reset',
  templateUrl: './reset.component.html',
  styleUrls: ['./reset.component.css']
})
export class ResetComponent {
  formData = {
    code: '',
    email: ''
  }
  message = "";

  constructor(@Inject(LOCAL_STORAGE) private storage: StorageService,private http: HttpClient,private router: Router) {

  }

  sendCode() {
    console.log("^^^");
    this.formData.email = this.storage.get("email");
    return this.http.post<any>("http://localhost:8086/api/v1/users/validateCode", this.formData).subscribe(
      data => {
        console.log("%%%");
        console.log(data);
        this.storage.set("Token", data.token);
        this.storage.set("PhotoId", data.photoId);
        this.storage.set("Username", data.username);
        this.storage.set("Id", data.id);
        this.router.navigate(['/form-password']);
      },
      error => {
        console.error('Eroare:', error);
        if (error.status == 401) {
          this.message = "Codul nu este valid!";
        }
      }
    );
  }
}