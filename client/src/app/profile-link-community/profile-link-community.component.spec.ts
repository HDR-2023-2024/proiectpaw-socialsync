import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileLinkCommunityComponent } from './profile-link-community.component';

describe('ProfileLinkCommunityComponent', () => {
  let component: ProfileLinkCommunityComponent;
  let fixture: ComponentFixture<ProfileLinkCommunityComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProfileLinkCommunityComponent]
    });
    fixture = TestBed.createComponent(ProfileLinkCommunityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
