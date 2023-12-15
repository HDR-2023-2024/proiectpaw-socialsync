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
    const scrollPosition = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;
    const windowHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight || 0;
    const documentHeight = document.documentElement.scrollHeight;

    return scrollPosition + windowHeight >= documentHeight;
  }
}
