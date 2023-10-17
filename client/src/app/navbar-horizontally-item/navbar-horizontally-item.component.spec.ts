import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarHorizontallyItemComponent } from './navbar-horizontally-item.component';

describe('NavbarHorizontallyItemComponent', () => {
  let component: NavbarHorizontallyItemComponent;
  let fixture: ComponentFixture<NavbarHorizontallyItemComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NavbarHorizontallyItemComponent]
    });
    fixture = TestBed.createComponent(NavbarHorizontallyItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
