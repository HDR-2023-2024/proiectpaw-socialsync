import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShortPostComponent } from './short-post.component';

describe('ShortPostComponent', () => {
  let component: ShortPostComponent;
  let fixture: ComponentFixture<ShortPostComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ShortPostComponent]
    });
    fixture = TestBed.createComponent(ShortPostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
