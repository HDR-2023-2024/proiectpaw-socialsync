import { Component } from '@angular/core';
import { Subscription } from 'rxjs';
import { PopupServiceService } from '../popup-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-popup-component',
  templateUrl: './popup-component.component.html',
  styleUrls: ['./popup-component.component.css']
})
export class PopupComponentComponent {
  isVisible = false;
  message = '';
  private popupSubscription: Subscription;

  constructor(private popupService: PopupServiceService, private router: Router) {
    this.popupSubscription = this.popupService.getPopupObservable().subscribe(

      data => {
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
    if (this.message.includes("Postarea a fost ștearsă")) {
      this.router.navigate(['/home']);
    } else if (this.message.includes("Comunitate ștearsă cu succes")) {
      this.router.navigate(['/view-topics']);
    } else if (this.message.includes("Porolă resetată")) {
      this.router.navigate(['/home']);
    }
  }
}



