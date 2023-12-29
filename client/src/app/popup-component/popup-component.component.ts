import { Component } from '@angular/core';
import { Subscription } from 'rxjs';
import { PopupServiceService } from '../popup-service.service';

@Component({
  selector: 'app-popup-component',
  templateUrl: './popup-component.component.html',
  styleUrls: ['./popup-component.component.css']
})
export class PopupComponentComponent {
  isVisible = false;
  message = '';
  private popupSubscription: Subscription;

  constructor(private popupService: PopupServiceService) {
    this.popupSubscription = this.popupService.getPopupObservable().subscribe(
  
      data => {
        console.log("Apelare afisare!");
        if (data.type === 'show') {
          this.message = data.message;
          this.isVisible = true;
        } else {
          this.isVisible = false;
        }
      }
    );
  }

  closePopup() {
    this.popupService.hidePopup();
  }

 
}
