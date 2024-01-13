import { Component, Inject,Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FullPostService } from '../full-post.service';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { UserServiceService } from '../user-service.service';
import { LOCAL_STORAGE, StorageService } from 'ngx-webstorage-service';
import { PopupServiceService } from '../popup-service.service';

@Component({
  selector: 'app-profile-link-profile',
  templateUrl: './profile-link-profile.component.html',
  styleUrls: ['./profile-link-profile.component.css']
})
export class ProfileLinkProfileComponent {

  constructor(@Inject(LOCAL_STORAGE) private storage: StorageService, private userServ: UserServiceService, public authService: AuthService, private http: HttpClient ,private popupService: PopupServiceService) { }
  @Input() userId: any = null;

  data = {
    id: '',
    photoId: '',
    username: '',
    email: '',
    role: '',
    gender: '',
    description: ''

  }
  ngOnInit() {
    let userId : any =  this.storage.get("userToShow");

    this.userServ.getData(userId).subscribe(
      (data) => {
        console.log('Datele de la server:', data);
        this.data = data;
      },
      (error) => {
        console.error('Eroare la incarcarea datelor:', error);
      }
    );
  }

  updateUser() {
    this.data.photoId = this.storage.get("ProfilePhoto");
    let headers = new HttpHeaders({
      'Authorization': this.authService.getToken()
    });
    console.log(this.data);
    this.http.put<any>("http://localhost:8086/api/v1/users", this.data, { headers: headers })
      .subscribe(
        data => {
          this.storage.set("PhotoId", this.data.photoId);
          this.popupService.showPopup("Modificările au fost făcute cu succes!");
        },
      );
  }
}

