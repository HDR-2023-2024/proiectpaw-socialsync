import { Component } from '@angular/core';
import { Injectable, Inject } from '@angular/core';
import { LOCAL_STORAGE, StorageService } from 'ngx-webstorage-service';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { Router } from '@angular/router';


@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent {
  formData = {
    nume: '',
    prenume: '',
    email: '',
    subject: '',
    message: ''
  };

  constructor(@Inject(LOCAL_STORAGE) private storage: StorageService, private http: HttpClient, private router: Router) { }
  // + email de trimis
  private loginUrl = 'http://localhost:8086/notification/send/madalina-elena.boaca%40student.tuiasi.ro';
  onSubmit() {
    this.send()
      .subscribe(
        response => {
          console.log('Create account:', response);
          if (response.status === 200) {
            alert("Mesaj trimis cu succes!");
          }
        },
        error => {
          if (error.status === 500) {
            this.router.navigate(['/internal-server-error']);
          }
        }
      )
  }

  send(): Observable<any> {
    console.log(this.formData);
    const postData = {
      nume: this.formData.nume, prenume: this.formData.prenume, email: this.formData.email,
      subject: this.formData.subject, message: this.formData.subject
    };
    return this.http.post(this.loginUrl, postData, { observe: 'response' }).pipe(
      tap((response: HttpResponse<any>) => {
        //console.log(response.body)
        return response;
      }),
      catchError(error => {
        return of(error);
      })
    );
  }
}


