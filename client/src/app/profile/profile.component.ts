import { Component } from '@angular/core';
import { ProfileLinkProfileComponent } from '../profile-link-profile/profile-link-profile.component';
import { ProfileLinkPostsComponent } from '../profile-link-posts/profile-link-posts.component';
import { ProfileLinkCommentsComponent } from '../profile-link-comments/profile-link-comments.component';
import { ProfileLinkSavedComponent } from '../profile-link-saved/profile-link-saved.component';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {
  myNavLinks = [
    { label: 'Profile', Component: ProfileLinkProfileComponent, active: true },
    { label: 'Posts', Component: ProfileLinkPostsComponent, active: false },
    { label: 'Comments', Component: ProfileLinkCommentsComponent, active: false },
  //  { label: 'Saved', Component: ProfileLinkSavedComponent, active: false },
  ];

}
