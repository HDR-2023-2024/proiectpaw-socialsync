import { Component } from '@angular/core';

@Component({
  selector: 'app-profile-link-profile',
  templateUrl: './profile-link-profile.component.html',
  styleUrls: ['./profile-link-profile.component.css']
})
export class ProfileLinkProfileComponent {
  user = {
    firstName: 'John',
    lastName: 'Doe',
    email: 'johndoe@example.com',
    password: '',
    passwordConfirm: ''
  };
}
