import { Component } from '@angular/core';
import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LOCAL_STORAGE, StorageService } from 'ngx-webstorage-service';
import { Router } from '@angular/router';
import { PopupServiceService } from '../popup-service.service';

@Component({
  selector: 'app-form-passwords',
  templateUrl: './form-passwords.component.html',
  styleUrls: ['./form-passwords.component.css']
})
export class FormPasswordsComponent {

  formData = {
    parola1: ' ',
    parola2: ' ',
    valid: false
  }
  message = ""
  constructor(@Inject(LOCAL_STORAGE) private storage: StorageService,private http: HttpClient, private popupService: PopupServiceService) { }

  resetPassword() {
    const token = this.storage.get('Token');

    const headers = new HttpHeaders({
      'Authorization': token
    });

    this.http.put<any>("http://localhost:8086/api/v1/users/updatePassword", {
      "password": this.formData.parola1
    },{ headers: headers }).subscribe(
      data => {
        console.log(data);
        this.popupService.showPopup('Porolă resetată cu succes!');
      },
      error => {
        console.error('Eroare:', error);
      }
    );
  }
} 
