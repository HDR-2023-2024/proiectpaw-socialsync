import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { faL } from '@fortawesome/free-solid-svg-icons';
import { CreatAccountService } from '../creat-account.service';
import { HttpClient } from '@angular/common/http';
import { ViewChild, ElementRef } from '@angular/core';

@Component({
  selector: 'app-create-account',
  templateUrl: './create-account.component.html',
  styleUrls: ['./create-account.component.css']
})
export class CreateAccountComponent {
  imageUrl: string | null = null;
  dataForm: FormData | null = null;
  formData: FormData | null = null;

  @ViewChild('fileInput') fileInputRef!: ElementRef<HTMLInputElement>;

  constructor(private router: Router, public authService: AuthService, public createAccount: CreatAccountService, private http: HttpClient) { }

  myFormData = {
    username: '',
    password: '',
    password2: '',
    email: '',
    photoId: null,
    gender: '',
    error: '',
    description:''
  };

  isValid = false;

  onSubmit() {
    this.createAccount.saveUser(this.myFormData.username, this.myFormData.email, this.myFormData.password, this.myFormData.photoId, this.myFormData.gender,this.myFormData.description)
      .subscribe(
        response => {
          console.log('Create account:', response);
          if (response.status === 200) {
            this.router.navigate(['/home']);
          }
          if (response.status === 406) {
            this.myFormData.error = response.error
          }
        },
        error => {
          if (error.status === 500) {
            this.router.navigate(['/internal-server-error']);
          }
          if (error.status === 406) {
            this.myFormData.error = error.error
          }
        }
      );
  }

  onPasswordChange(): void {
    if (this.myFormData.password != this.myFormData.password2) {
      this.myFormData.error = "Cele doua parole nu corespund!";
      this.isValid = true;

    } else {
      this.myFormData.error = "";
      this.isValid = false;
    }
  }

  uploadFiles() {
    const fileInput = this.fileInputRef.nativeElement;
    const files = fileInput.files;

    if (files && files.length > 0) {
      this.formData = new FormData();

      for (let i = 0; i < files.length; i++) {
        this.formData.append('files', files[i]);
      }

      this.http.post<any>('http://localhost:8086/api/v1/storage/upload-multipartFile', this.formData)
        .subscribe(
          data => {
            console.log(data);
            if (data && data.length > 0) {
              this.imageUrl = data[0].url;
              this.myFormData.photoId = data[0].url;
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
