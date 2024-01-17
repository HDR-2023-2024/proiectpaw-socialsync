import { Component, Input } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { PopupServiceService } from '../popup-service.service';
import { HttpHeaders, HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-short-post',
  templateUrl: './short-post.component.html',
  styleUrls: ['./short-post.component.css']
})
export class ShortPostComponent {
  @Input() data: any;

  constructor(public authService: AuthService, private http: HttpClient, private router: Router, private popupService: PopupServiceService) { }

  navigateToPost(postId: number) {
    this.router.navigate(['/full-post', postId]);
  }

  ngOnInit() {
    var timestamp = this.data?.timestampCreated;

    var date = new Date(timestamp * 1000);

    var year = date.getFullYear();
    var month = ("0" + (date.getMonth() + 1)).slice(-2);
    var day = ("0" + date.getDate()).slice(-2);
    var hours = ("0" + date.getHours()).slice(-2);
    var minutes = ("0" + date.getMinutes()).slice(-2);
    var formattedDate = year + "-" + month + "-" + day + " " + hours + ":" + minutes;

    //console.log(formattedDate);
    this.data.timestampCreated = formattedDate;
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
          }
        );
    }

    console.log(this.data.dislikedByUser)
  }


  upvote() {
    if (!this.authService.getToken()) {
      this.popupService.showPopup('Este necesară autentificarea pentru a vota. Vă rugăm să vă autentificați.');
      return;
    }
    const headers = new HttpHeaders({
      'Authorization': this.authService.getToken()
    });
    if (this.data.dislikedByUser) {
      this.data.score++;
    }
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
              this.popupService.showPopup('Sesiunea a expirat este necesară reautentificarea.');
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
              this.popupService.showPopup('Sesiunea a expirat este necesară reautentificarea.');
              this.router.navigate(['/login']);
            }
          }
        );
    }

  }

  navigateToCommunity(id: string) {
    this.router.navigate(['community/', id]);
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
            this.popupService.showPopup('Ai raportat deja această postare!');
          }
        }
      );
  }

  navigateToUser(){

    this.router.navigate(['/user', this.data.creator?.id]);
  }
}
