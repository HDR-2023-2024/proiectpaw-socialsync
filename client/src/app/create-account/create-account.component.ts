import { Component } from '@angular/core';

@Component({
  selector: 'app-create-account',
  templateUrl: './create-account.component.html',
  styleUrls: ['./create-account.component.css']
})
export class CreateAccountComponent {
  formData = {
    username: '',
    password: '',
    password2: '',
    email: '',
    gender: ''
  };
  
  onSubmit() {

    console.log(this.formData);

  }
}
