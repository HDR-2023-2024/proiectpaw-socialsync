import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NotificationsComponent } from './notifications.component';
import { NavbarDisconnectedComponent } from '../navbar-disconnected/navbar-disconnected.component';
import { HttpClientModule } from '@angular/common/http';
import { AuthService } from '../auth.service';
import { NavbarHorizontallyComponent } from '../navbar-horizontally/navbar-horizontally.component';
import { CarouselComponent } from '../carousel/carousel.component';
import { PopupComponentComponent } from '../popup-component/popup-component.component';
import { NotificationComponent } from '../notification/notification.component';
import { NavbarHorizontallyItemComponent } from '../navbar-horizontally-item/navbar-horizontally-item.component';

describe('NotificationsComponent', () => {
  let component: NotificationsComponent;
  let fixture: ComponentFixture<NotificationsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NotificationsComponent, NavbarHorizontallyComponent, NavbarHorizontallyItemComponent, CarouselComponent, NavbarDisconnectedComponent, PopupComponentComponent, NotificationComponent],
      imports:[HttpClientModule],
      providers: [AuthService]
    });
    fixture = TestBed.createComponent(NotificationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
