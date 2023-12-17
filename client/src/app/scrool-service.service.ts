import { Injectable , HostListener, EventEmitter} from '@angular/core';
import { fromEvent, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ScroolServiceService {
  private scrollEvent$: Observable<Event>;

  constructor() {
    this.scrollEvent$ = fromEvent(window, 'scroll');
  }

  getScrollObservable(): Observable<Event> {
    return this.scrollEvent$;
  }

  isScrolledToBottom(): boolean {
    const isAtBottom = window.pageYOffset + window.innerHeight >= document.documentElement.scrollHeight;

    return isAtBottom;
    
  }
}
