import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileCommentComponent } from './profile-comment.component';

describe('ProfileCommentComponent', () => {
  let component: ProfileCommentComponent;
  let fixture: ComponentFixture<ProfileCommentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProfileCommentComponent]
    });
    fixture = TestBed.createComponent(ProfileCommentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
