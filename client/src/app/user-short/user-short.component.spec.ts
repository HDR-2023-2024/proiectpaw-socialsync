import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserShortComponent } from './user-short.component';

describe('UserShortComponent', () => {
  let component: UserShortComponent;
  let fixture: ComponentFixture<UserShortComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserShortComponent]
    });
    fixture = TestBed.createComponent(UserShortComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
