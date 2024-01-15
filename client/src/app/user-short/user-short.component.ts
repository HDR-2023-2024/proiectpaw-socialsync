import { Component, Input } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { PopupServiceService } from '../popup-service.service';
import { HttpHeaders, HttpClient } from '@angular/common/http';


@Component({
  selector: 'app-user-short',
  templateUrl: './user-short.component.html',
  styleUrls: ['./user-short.component.css']
})
export class UserShortComponent {
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

 
  navigateToUser(id: string) {
    this.router.navigate(['user/', id]);
  }

}
