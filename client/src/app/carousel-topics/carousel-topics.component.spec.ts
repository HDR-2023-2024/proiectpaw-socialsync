import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarouselTopicsComponent } from './carousel-topics.component';

describe('CarouselTopicsComponent', () => {
  let component: CarouselTopicsComponent;
  let fixture: ComponentFixture<CarouselTopicsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CarouselTopicsComponent]
    });
    fixture = TestBed.createComponent(CarouselTopicsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
