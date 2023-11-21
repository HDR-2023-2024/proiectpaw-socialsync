import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  formData = {
    username: '',
    password: ''
  };
  
  constructor(private router: Router, public authService: AuthService) { }

  onLoginClick() {
    this.authService.login();
    this.router.navigate(['/home']);
  }
}
