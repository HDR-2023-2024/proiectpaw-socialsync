import { Component, Input } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

import { HttpHeaders, HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-short-post',
  templateUrl: './short-post.component.html',
  styleUrls: ['./short-post.component.css']
})
export class ShortPostComponent {
  @Input() data: any;

  constructor(public authService: AuthService, private http: HttpClient, private router: Router) { }

  navigateToPost(postId: number) {
    this.router.navigate(['/full-post', postId]);
  }

  ngOnInit() {
    var timestamp = this.data?.timestampCreated;

    var date = new Date(timestamp);

    var year = date.getFullYear();
    var month = ("0" + (date.getMonth() + 1)).slice(-2); 
    var day = ("0" + date.getDate()).slice(-2);
    var hours = ("0" + date.getHours()).slice(-2);
    var minutes = ("0" + date.getMinutes()).slice(-2);
    var formattedDate = year + "-" + month + "-" + day + " " + hours + ":" + minutes;

    console.log(formattedDate);
    this.data.timestampCreated = formattedDate;
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
            this.data.score--;
            this.data.dislikedByUser = true;
            this.data.likedByUser = false;
          },
          error => {
            console.error('Eroare:', error);
            if (error.status == 401) {
              this.router.navigate(['/unauthorized']);
            }
          }
        );
    } else {
      this.http.put<any>("http://localhost:8086/api/v1/posts/" + this.data.id + "/downvote", '', { headers: headers })
        .subscribe(
          data => {
            //console.log(data);
            this.data.score++;
            this.data.dislikedByUser = false;
            this.data.likedByUser = false;
          },
          error => {
            console.error('Eroare:', error);
            if (error.status == 401) {
              this.router.navigate(['/unauthorized']);
            }
          }
        );
    }
    console.log(this.data.dislikedByUser)
  }


  upvote() {
    if (!this.authService.getToken()) {
      alert('Este necesară autentificarea pentru a vota. Vă rugăm să vă autentificați.');
      return;
    }
    const headers = new HttpHeaders({
      'Authorization': this.authService.getToken()
    });
    //console.log("upvote");
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

      // e aceiasi metoda
      this.http.put<any>("http://localhost:8086/api/v1/posts/" + this.data.id + "/upvote", '', { headers: headers })
        .subscribe(
          data => {
            //console.log(data);
            this.data.score--;
            this.data.likedByUser = false;
            this.data.dislikedByUser = false;
          },
          error => {
            console.error('Eroare:', error);
            if (error.status == 401) {
              this.router.navigate(['/unauthorized']);
            }
          }
        );
    }
  }


}
