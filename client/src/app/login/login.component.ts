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
  loginMsg = ""

  constructor(private router: Router, public authService: AuthService) { }

  onLoginClick() {
    this.authService.login(this.formData.username, this.formData.password)
      .subscribe(
        response => {
          if(response.body.role !== 'user'){
            this.loginMsg = "Nu este un cod de utilizator!"
          }else{
          if (response.status === 200) {
            this.router.navigate(['/home']);
          }
          else if (response.status === 401) {
            this.loginMsg = "Datele de autentificare sunt invalide!"
          }
          else if (response.status === 406) {
            this.loginMsg = "Datele de autentificare sunt invalide!"
          }
        }
        },
        error => {
          if (error.status === 401) {
            this.loginMsg = "Datele de autentificare sunt invalide!"
          }
          if (error.status === 406) {
            this.loginMsg = "Datele de autentificare sunt invalide!"
          }
        }
      );
  }
}
