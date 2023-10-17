import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarDisconnectedComponent } from './navbar-disconnected.component';

describe('NavbarDisconnectedComponent', () => {
  let component: NavbarDisconnectedComponent;
  let fixture: ComponentFixture<NavbarDisconnectedComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NavbarDisconnectedComponent]
    });
    fixture = TestBed.createComponent(NavbarDisconnectedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
