import { Component ,Input} from '@angular/core';
import { ProfileLinkProfileComponent } from '../profile-link-profile/profile-link-profile.component';
import { ProfileLinkPostsComponent } from '../profile-link-posts/profile-link-posts.component';
import { ProfileLinkCommunityComponent } from '../profile-link-community/profile-link-community.component';
import { ActivatedRoute } from '@angular/router';
import { FullPostService } from '../full-post.service';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { CreatePostService } from '../create-post.service';
import { PopupServiceService } from '../popup-service.service';
import {  Inject } from '@angular/core';
import { LOCAL_STORAGE, StorageService } from 'ngx-webstorage-service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {

  constructor(private route: ActivatedRoute, private postService: FullPostService, public authService: AuthService, private http: HttpClient, private router: Router, private createPostService: CreatePostService, private popupService: PopupServiceService,@Inject(LOCAL_STORAGE) private storage: StorageService) { }

  userId : string | null = null;

  myNavLinks = [
    { label: 'Profil', Component: ProfileLinkProfileComponent, active: true },
    { label: 'Postări', Component: ProfileLinkPostsComponent, active: false },
    { label: 'Comunități', Component: ProfileLinkCommunityComponent, active: false },
    //  { label: 'Saved', Component: ProfileLinkSavedComponent, active: false },
  ];

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.userId = params['userId'];
      if(this.userId == undefined){
        this.userId = this.authService.getId();
      }
      this.storage.set("userToShow", this.userId);
      console.log(this.userId)
    });
  }
}
