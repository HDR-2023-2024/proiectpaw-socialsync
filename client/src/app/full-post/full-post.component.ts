import { Component, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FullPostService } from '../full-post.service';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { CreatePostService } from '../create-post.service';
import { PopupServiceService } from '../popup-service.service';

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
    "timestampCreated": 0

  }

  private page = 0;
  postId: string = '';

  constructor(private route: ActivatedRoute, private postService: FullPostService, public authService: AuthService, private http: HttpClient, private router: Router, private createPostService: CreatePostService, private popupService: PopupServiceService) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.postId = params['id'];
      this.loadPostDetails();
    });
  }

  loadPostDetails() {
    this.postService.getData(this.postId, this.page.toString()).subscribe(
      (data) => {
        //console.log('Datele de la server:', data);
        this.data = data;
      },
      (error) => {
        console.error('Eroare la incarcarea datelor:', error);
        if (error.status === 500) {
          this.router.navigate(['/internal-server-error']);
        }
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
      this.popupService.showPopup('Este necesară autentificarea pentru a vota. Vă rugăm să vă autentificați.');
      return;
    }
    const headers = new HttpHeaders({
      'Authorization': this.authService.getToken()
    });
    if (this.data.likedByUser) {
      this.data.score--;
    }
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
              this.popupService.showPopup('Sesiunea a expirat este necesară reautentificarea.');
              this.router.navigate(['/login']);
            }
            if (error.status === 500) {
              this.router.navigate(['/internal-server-error']);
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
              this.popupService.showPopup('Sesiunea a expirat este necesară reautentificarea.');

              this.router.navigate(['/login']);
            }
            if (error.status === 500) {
              this.router.navigate(['/internal-server-error']);
            }
          }
        );
    }
    //console.log(this.data.dislikedByUser)
  }


  upvote() {
    if (!this.authService.getToken()) {
      this.popupService.showPopup('Este necesară autentificarea pentru a vota. Vă rugăm să vă autentificați.');
      return;
    }
    const headers = new HttpHeaders({
      'Authorization': this.authService.getToken()
    });
    //console.log("upvote");
    if (this.data.dislikedByUser) {
      this.data.score++;
    }
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
              this.popupService.showPopup('Sesiunea a expirat este necesară reautentificarea.');
              this.router.navigate(['/login']);
            }
            if (error.status === 500) {
              this.router.navigate(['/internal-server-error']);
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
              this.popupService.showPopup('Sesiunea a expirat este necesară reautentificarea.');
              this.router.navigate(['/login']);
            }
            if (error.status === 500) {
              this.router.navigate(['/internal-server-error']);
            }
          }
        );
    }
  }
  deletePost() {
    this.createPostService.deletePost(this.data.id);
    this.popupService.showPopup('Postarea a fost ștearsă cu succes.');
    // this.router.navigate(['/home']);

  }

  editPost() {
    this.router.navigate(['edit-post/', this.data.id]);
  }

  raportPost() {
    console.log("Ceva2")
    if (!this.authService.getToken()) {
      this.popupService.showPopup('Este necesară autentificarea pentru a raporta. Vă rugăm să vă autentificați.');
      return;
    }
    const headers = new HttpHeaders({
      'Authorization': this.authService.getToken()
    });
   
    this.http.post<any>("http://localhost:8086/notification/reports", {
      "userId": this.authService.getId(),
      "username": this.authService.getUsername(),
      "postId": this.data.id,
      "postTitle": this.data.title
    }, { headers: headers })
      .subscribe(
        data => {
          this.popupService.showPopup('Postarea a fost raportată cu succes!');
        },
        error => {
          console.error('Eroare:', error);
          if (error.status == 401) {
            this.router.navigate(['/unauthorized']);
          }
          if (error.status == 403) {
            this.popupService.showPopup('Sesiunea a expirat este necesară reautentificarea.');
            this.router.navigate(['/login']);
          }
          if (error.status == 200) {
     
            this.popupService.showPopup('Postarea a fost raportată cu succes!');
          }
          if (error.status == 422) {
     
            this.popupService.showPopup('Postarea a fost deja raportată cu succes!');
          }
        }
      );
  }

  navigateToCommunity(id: string) {
    this.router.navigate(['community/', id]);
  }

  navigateToUser(){
    this.router.navigate(['/user', this.data.creator?.id]);
  }
}
