import { Component } from '@angular/core';
import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LOCAL_STORAGE, StorageService } from 'ngx-webstorage-service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-reset-pass',
  templateUrl: './reset-pass.component.html',
  styleUrls: ['./reset-pass.component.css']
})
export class ResetPassComponent {
  formData = {
    email: ''
  };

  message = "";

  constructor(@Inject(LOCAL_STORAGE) private storage: StorageService,private http: HttpClient,private router: Router) {

  }
  sendCode() {
     this.http.post<any>("http://localhost:8086/api/v1/users/sendCodeResetPassword", {
      "email": this.formData.email
    }).subscribe(
      data => {
        console.log(data);
        this.storage.set("email",this.formData.email);
        this.router.navigate(['/reset']);
      },
      error => {
        console.error('Eroare:', error);
        if (error.status == 404) {
          this.message = "Adresă de email nu există!";
        }
        if (error.status == 500) {
          this.router.navigate(['/internal-server-error']);
        }
      }
    );
  }
}