import { Component ,Inject} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FullPostService } from '../full-post.service';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { UserServiceService } from '../user-service.service';
import { LOCAL_STORAGE, StorageService } from 'ngx-webstorage-service';

@Component({
  selector: 'app-profile-link-profile',
  templateUrl: './profile-link-profile.component.html',
  styleUrls: ['./profile-link-profile.component.css']
})
export class ProfileLinkProfileComponent {

  constructor( @Inject(LOCAL_STORAGE) private storage: StorageService,private userServ: UserServiceService,public authService: AuthService ) { }


  data = {
    id: '',
    photoId: '',
    username: '',
    email: '',
    role: '',
    gender: '',
}
ngOnInit() {
  this.userServ.getData(this.authService.getId() ).subscribe(
    (data) => {
      console.log('Datele de la server:', data);
      this.data = data;
    },
    (error) => {
      console.error('Eroare la incarcarea datelor:', error);
    }
  );
}
}
