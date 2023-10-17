import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarLoggendComponent } from './navbar-loggend.component';

describe('NavbarLoggendComponent', () => {
  let component: NavbarLoggendComponent;
  let fixture: ComponentFixture<NavbarLoggendComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NavbarLoggendComponent]
    });
    fixture = TestBed.createComponent(NavbarLoggendComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
