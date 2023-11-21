import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { faL } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-create-account',
  templateUrl: './create-account.component.html',
  styleUrls: ['./create-account.component.css']
})
export class CreateAccountComponent {

  constructor(private router: Router, public authService: AuthService) { }

  formData = {
    username: '',
    password: '',
    password2: '',
    email: '',
    gender: '',
    error: ''
  };

  isValid = false;
  
  onSubmit() {
    this.router.navigate(['/registration-successful']);
  }

  onPasswordChange(): void {
    if(this.formData.password != this.formData.password2){
      this.formData.error = "Cele doua parole nu corespund!";
      this.isValid = true;
      
    }else{
      this.formData.error = "";
      this.isValid = false;
    }
  }
}
