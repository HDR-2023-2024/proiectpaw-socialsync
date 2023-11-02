import { Component } from '@angular/core';

@Component({
  selector: 'app-profile-card',
  templateUrl: './profile-card.component.html',
  styleUrls: ['./profile-card.component.css']
})
export class ProfileCardComponent {
  handleImageChange(event: Event) {
    const inputElement = event.target as HTMLInputElement;
    if (inputElement.files && inputElement.files.length > 0) {
      const selectedFile = inputElement.files[0];

      // Handle the selected file here (e.g., upload to server, display as profile picture, etc.)
      // You can implement this logic as needed.
      const profileImage = document.getElementById('profileImage') as HTMLImageElement;

      if (profileImage) {
        const reader = new FileReader();
        reader.onload = (e) => {
          console.log('Event:', e);
          if (e && e.target && e.target.result) {
            console.log('Data URL:', e.target.result);
            // Display the selected image in the img element
            profileImage.src = e.target.result as string;
          } else {
            console.error('Event data is missing.');
          }
        };
        reader.readAsDataURL(selectedFile);
      }
    }
  }
}
