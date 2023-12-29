import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PopupComponentComponent } from './popup-component.component';

describe('PopupComponentComponent', () => {
  let component: PopupComponentComponent;
  let fixture: ComponentFixture<PopupComponentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PopupComponentComponent]
    });
    fixture = TestBed.createComponent(PopupComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
