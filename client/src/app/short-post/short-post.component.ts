import { Component, Input } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { faL } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-short-post',
  templateUrl: './short-post.component.html',
  styleUrls: ['./short-post.component.css']
})
export class ShortPostComponent {
  @Input() data: any
  constructor(public authService: AuthService, private router: Router, private http: HttpClient) { }

  navigateToPost(postId: number) {
    this.router.navigate(['/full-post', postId]);
  }

  downvote() {
    //console.log("downvote");
    if (!this.authService.getToken()) {
      alert('Este necesară autentificarea pentru a vota. Vă rugăm să vă autentificați.');
      return;
    }

    const headers = new HttpHeaders({
      'Authorization': this.authService.getToken()
    });

    if (this.data.dislikedByUser == false) {
      this.http.put<any>("http://localhost:8086/api/v1/posts/" + this.data.id + "/downvote", '', { headers: headers })
        .subscribe(
          data => {
            //console.log(data);
            this.data.score++;
            this.data.dislikedByUser = true;
            this.data.likedByUser = false;
          },
          error => {
            console.error('Eroare:', error);
          }
        );
    } else {
      this.data.score--;
      this.data.dislikedByUser = false;
    }
    console.log(this.data.dislikedByUser)
  }


  upvote() {
    if (!this.authService.getToken()) {
      alert('Este necesară autentificarea pentru a vota. Vă rugăm să vă autentificați.');
      return;
    }
    //console.log("upvote");
    const headers = new HttpHeaders({
      'Authorization': this.authService.getToken()
    });
    if (this.data.likedByUser == false) {
      this.http.put<any>("http://localhost:8086/api/v1/posts/" + this.data.id + "/upvote", '', { headers: headers })
        .subscribe(
          data => {
            //console.log(data);
            this.data.score++;
            this.data.likedByUser = true;
            this.data.dislikedByUser = false;
          },
          error => {
            console.error('Eroare:', error);
            if (error.status == 401) {
              this.router.navigate(['/unauthorized']);
            }
          }
        );
    } else {
      this.data.score--;
      this.data.likedByUser = false;
    }
  }

}
