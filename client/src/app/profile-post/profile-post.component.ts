import {Component, Input} from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
 
@Component({
  selector: 'app-profile-post',
  templateUrl: './profile-post.component.html',
  styleUrls: ['./profile-post.component.css']
})
export class ProfilePostComponent {

  constructor(public authService: AuthService,private router: Router) { }

  @Input() data: any;
  navigateToPost(postId: number) {
    this.router.navigate(['/full-post', postId]);
  }
}
