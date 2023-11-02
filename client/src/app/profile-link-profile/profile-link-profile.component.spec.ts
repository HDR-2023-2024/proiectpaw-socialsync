import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileLinkProfileComponent } from './profile-link-profile.component';

describe('ProfileLinkProfileComponent', () => {
  let component: ProfileLinkProfileComponent;
  let fixture: ComponentFixture<ProfileLinkProfileComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProfileLinkProfileComponent]
    });
    fixture = TestBed.createComponent(ProfileLinkProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
