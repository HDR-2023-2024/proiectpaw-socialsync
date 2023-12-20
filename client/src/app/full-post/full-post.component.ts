import { Component, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FullPostService } from '../full-post.service';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { CreatePostService } from '../create-post.service';

@Component({
  selector: 'app-full-post',
  templateUrl: './full-post.component.html',
  styleUrls: ['./full-post.component.css']
})
export class FullPostComponent {

  data: any = {
    
      id: '',
      "creator": {
          "id": '',
          "photoId": '',
          "username": "",
          "role": ""
      },
      "topic": {
          "id": "",
          "title": "",
          "photoId": ""
      },
      "title": "",
      "content": "",
      "likedByUser": false,
      "dislikedByUser": false,
      "comments": [
      ],
      "photos": [],
      "score": -1,
      "timestampCreated": 1703055619
  
  }

  private page = 0;
  postId: string = '';

  constructor(private route: ActivatedRoute, private postService: FullPostService, public authService: AuthService, private http: HttpClient, private router: Router, private createPostService : CreatePostService) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.postId = params['id'];
      this.loadPostDetails();
    });
  }

  loadPostDetails() {
    this.postService.getData(this.postId, this.page.toString()).subscribe(
      (data) => {
        console.log('Datele de la server:', data);
        this.data = data;
        console.log("hei");
      },
      (error) => {
        console.error('Eroare la incarcarea datelor:', error);
      }
    );
  }

  convertTimestampToDateTime(timestamp: number) {
    var date = new Date(timestamp * 1000);
    return date.toLocaleString();
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
            if (error.status == 403) {
              alert('Sesiunea a expirat este necesară reautentificarea.');
              this.router.navigate(['/login']);
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
            if (error.status == 403) {
              alert('Sesiunea a expirat este necesară reautentificarea.');
              this.router.navigate(['/login']);
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
            if (error.status == 403) {
              alert('Sesiunea a expirat este necesară reautentificarea.');
              this.router.navigate(['/login']);
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
            if (error.status == 403) {
              alert('Sesiunea a expirat este necesară reautentificarea.');
              this.router.navigate(['/login']);
            }
          }
        );
    }
  }
  deletePost(){
    this.createPostService.deletePost(this.data.id);
  }

  editPost(){

  }
  
}
