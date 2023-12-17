import { Component, Input } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { HttpClient ,HttpHeaders} from '@angular/common/http';


@Component({
  selector: 'app-topic-short',
  templateUrl: './topic-short.component.html',
  styleUrls: ['./topic-short.component.css']
})
export class TopicShortComponent {
  @Input() data: any
  constructor(public authService: AuthService, private router: Router, private http: HttpClient) { }

  navigateToPost(postId: number) {
    this.router.navigate(['/full-post', postId]);
  }

  downvote() {
    //console.log("downvote");
    const headers = new HttpHeaders({
      'Authorization': this.authService.getToken() 
    });
    this.http.put<any>("http://localhost:8086/api/v1/posts/" + this.data.id + "/downvote", '', { headers: headers })
      .subscribe(
        data => {
          //console.log(data);
          this.data.score--; 
        },
        error => {
          console.error('Eroare:', error);
        }
      );
  }
  
  upvote() {
    //console.log("upvote");
    const headers = new HttpHeaders({
      'Authorization': this.authService.getToken() 
    });
  
    this.http.put<any>("http://localhost:8086/api/v1/posts/" + this.data.id + "/upvote", '', { headers: headers })
      .subscribe(
        data => {
          //console.log(data);
          this.data.score++; 
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
