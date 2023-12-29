import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PopupServiceService {
  private popupSubject = new Subject<any>();

  showPopup(message: string) {
    console.log("Pop-up afisat!");
    this.popupSubject.next({ type: 'show', message: message });
  }

  hidePopup() {
    this.popupSubject.next({ type: 'hide' });
  }

  getPopupObservable() {
    return this.popupSubject.asObservable();
  }
}
