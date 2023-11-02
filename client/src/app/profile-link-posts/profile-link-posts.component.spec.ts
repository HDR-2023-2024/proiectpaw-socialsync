import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileLinkPostsComponent } from './profile-link-posts.component';

describe('ProfileLinkPostsComponent', () => {
  let component: ProfileLinkPostsComponent;
  let fixture: ComponentFixture<ProfileLinkPostsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProfileLinkPostsComponent]
    });
    fixture = TestBed.createComponent(ProfileLinkPostsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
