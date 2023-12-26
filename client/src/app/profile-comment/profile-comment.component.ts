import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-profile-comment',
  templateUrl: './profile-comment.component.html',
  styleUrls: ['./profile-comment.component.css']
})

export class ProfileCommentComponent {
  constructor(public authService: AuthService, private router: Router) { }

  @Input() data: any;
  navigateToPost(postId: number) {
    this.router.navigate(['/full-post', postId]);
  }

}
