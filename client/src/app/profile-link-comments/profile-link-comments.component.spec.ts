import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileLinkCommentsComponent } from './profile-link-comments.component';

describe('ProfileLinkCommentsComponent', () => {
  let component: ProfileLinkCommentsComponent;
  let fixture: ComponentFixture<ProfileLinkCommentsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProfileLinkCommentsComponent]
    });
    fixture = TestBed.createComponent(ProfileLinkCommentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
