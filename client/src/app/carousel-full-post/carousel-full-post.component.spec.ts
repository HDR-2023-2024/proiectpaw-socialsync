import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarouselFullPostComponent } from './carousel-full-post.component';

describe('CarouselFullPostComponent', () => {
  let component: CarouselFullPostComponent;
  let fixture: ComponentFixture<CarouselFullPostComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CarouselFullPostComponent]
    });
    fixture = TestBed.createComponent(CarouselFullPostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
