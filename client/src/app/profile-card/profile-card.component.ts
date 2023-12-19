import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FullPostService } from '../full-post.service';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { UserServiceService } from '../user-service.service';

@Component({
  selector: 'app-profile-card',
  templateUrl: './profile-card.component.html',
  styleUrls: ['./profile-card.component.css']
})
export class ProfileCardComponent {
  constructor( private user: UserServiceService,public authService: AuthService ) { }
  data = {
    id: '',
    photoId: '',
    username: '',
    email: '',
    role: '',
    gender: ''
}

  ngOnInit() {
    this.user.getData(this.authService.getId() ).subscribe(
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
