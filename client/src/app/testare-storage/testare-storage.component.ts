import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {  ViewChild, ElementRef } from '@angular/core';

@Component({
  selector: 'app-testare-storage',
  templateUrl: './testare-storage.component.html',
  styleUrls: ['./testare-storage.component.css']
})
export class TestareStorageComponent {
  imageUrl: string | null = null;

  @ViewChild('fileInput') fileInputRef!: ElementRef<HTMLInputElement>;

  constructor(private http: HttpClient) {}

  uploadFiles() {
    const fileInput = this.fileInputRef.nativeElement;
    const files = fileInput.files;

    if (files && files.length > 0) {
      const formData = new FormData();

      for (let i = 0; i < files.length; i++) {
        formData.append('files', files[i]);
      }

      this.http.post<any>('http://localhost:8086/api/v1/storage/upload-multipartFile', formData)
        .subscribe(
          data => {
            console.log(data);
            if (data && data.length > 0) {
              this.imageUrl =  data[0].url; 
            }
          },
          error => {
            console.error('Eroare:', error);
          }
        );
    } else {
      console.error('Niciun fisier. selectat.');
    }
  }
}
