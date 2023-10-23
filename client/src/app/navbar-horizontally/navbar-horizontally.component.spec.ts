import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarHorizontallyComponent } from './navbar-horizontally.component';

describe('NavbarHorizontallyComponent', () => {
  let component: NavbarHorizontallyComponent;
  let fixture: ComponentFixture<NavbarHorizontallyComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NavbarHorizontallyComponent]
    });
    fixture = TestBed.createComponent(NavbarHorizontallyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
