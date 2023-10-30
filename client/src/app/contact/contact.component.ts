import { Component } from '@angular/core';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent {
  formData = {
    fname: '',
    lname: '',
    email: '',
    subject: '',
    message: ''
  };
  
  onSubmit() {

    console.log(this.formData);

  }
}
