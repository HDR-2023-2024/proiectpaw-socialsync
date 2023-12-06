import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { faL } from '@fortawesome/free-solid-svg-icons';
import { CreatAccountService } from '../creat-account.service';

@Component({
  selector: 'app-create-account',
  templateUrl: './create-account.component.html',
  styleUrls: ['./create-account.component.css']
})
export class CreateAccountComponent {

  constructor(private router: Router,public authService:AuthService, public createAccount : CreatAccountService) { }

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
    this.createAccount.saveUser(this.formData.username,this.formData.email, this.formData.password,this.formData.gender)
    .subscribe(
      response => {
        console.log('Login sresponse:', response);
        if(response.status === 200){
          this.router.navigate(['/home']);
        }
        if(response.status === 406){
          this.formData.error = response.error
        }
      },
      error => {
        if(error.status === 500){
          this.router.navigate(['/internal-server-error']);
        }
        if(error.status === 406){
          this.formData.error = error.error
        }
      }
    );
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
