import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileLinkSavedComponent } from './profile-link-saved.component';

describe('ProfileLinkSavedComponent', () => {
  let component: ProfileLinkSavedComponent;
  let fixture: ComponentFixture<ProfileLinkSavedComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProfileLinkSavedComponent]
    });
    fixture = TestBed.createComponent(ProfileLinkSavedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
