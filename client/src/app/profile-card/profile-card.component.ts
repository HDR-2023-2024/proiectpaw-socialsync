import { Component, Inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FullPostService } from '../full-post.service';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { UserServiceService } from '../user-service.service';
import { ViewChild, ElementRef } from '@angular/core';
import { LOCAL_STORAGE, StorageService } from 'ngx-webstorage-service';

@Component({
  selector: 'app-profile-card',
  templateUrl: './profile-card.component.html',
  styleUrls: ['./profile-card.component.css']
})
export class ProfileCardComponent {
  constructor(@Inject(LOCAL_STORAGE) private storage: StorageService, private user: UserServiceService, private router: Router, public authService: AuthService, private http: HttpClient) { }
  data = {
    id: '',
    photoId: '',
    username: '',
    email: '',
    role: '',
    gender: ''
  }
  profilePhoto = '../../assets/images/profile_pic.jpg';

  @ViewChild('fileInput') fileInputRef!: ElementRef<HTMLInputElement>;

  ngOnInit() {
    let userId : any =  this.storage.get("userToShow");
    this.user.getData(userId).subscribe(
      (data) => {
        console.log('Datele de la server:', data);
        this.data = data;
        this.profilePhoto =  data.photoId;
      },
      (error) => {
        this.router.navigate(['/internal-server-error']);
        console.error('Eroare la incarcarea datelor:', error);
      }
    );
  }

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
            if (data && data.length > 0) {
              console.log(data)
              this.storage.set("ProfilePhoto", data[0].url);
              this.profilePhoto = data[0].url;
              this.data.photoId = data[0].url;
            }
          },
          error => {
            this.router.navigate(['/internal-server-error']);
            console.error('Eroare:', error);
          }
        );
    } else {
      this.router.navigate(['/internal-server-error']);
      console.error('Niciun fisier. selectat.');
    }
    
  }

}
