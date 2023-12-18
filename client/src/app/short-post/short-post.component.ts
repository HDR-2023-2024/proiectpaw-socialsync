import { Component,Input } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-short-post',
  templateUrl: './short-post.component.html',
  styleUrls: ['./short-post.component.css']
})
export class ShortPostComponent {
  @Input() data: any;

  constructor(public authService : AuthService,private router: Router){}

  navigateToPost(postId: number) {
    this.router.navigate(['/full-post', postId]);
  }
}
