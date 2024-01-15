import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PopupServiceService {
  private popupSubject = new Subject<any>();

  showPopup(message: string) {
    this.popupSubject.next({ type: 'show', message: message });
  }

  hidePopup() {
    this.popupSubject.next({ type: 'hide' });
  }

  getPopupObservable() {
    return this.popupSubject.asObservable();
  }

}
