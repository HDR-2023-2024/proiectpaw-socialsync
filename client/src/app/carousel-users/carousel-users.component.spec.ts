import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarouselUsersComponent } from './carousel-users.component';

describe('CarouselUsersComponent', () => {
  let component: CarouselUsersComponent;
  let fixture: ComponentFixture<CarouselUsersComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CarouselUsersComponent]
    });
    fixture = TestBed.createComponent(CarouselUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
