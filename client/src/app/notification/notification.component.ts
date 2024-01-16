import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { PopupServiceService } from '../popup-service.service';
import { HttpHeaders, HttpClient } from '@angular/common/http';

interface Notification {
  tip: number,
  id: string;
  creatorId: string;
  topicId: string;
  messageType: string | null;
  postId: string | null;
  title: string;
  topicTitle: string
  postTitle: string
  comm:string
}

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.css']
})
export class NotificationComponent {
  constructor( private router: Router,public authService : AuthService,private popupService: PopupServiceService,private http: HttpClient) {}
  @Input() data: Notification  = {
    tip: 0,
    id: '',
    creatorId: '',
    topicId: '',
    messageType: '',
    postId: '',
    title: '',
    topicTitle: '',
    postTitle: '',
    comm:''
  }
  navigateToCommunity(id: string) {
    this.router.navigate(['community/', id]);
  }

  navigateToPost(id: string | null) {
    this.router.navigate(['full-post/', id]);
  }

  deleteNotification(){
    const headers = new HttpHeaders({
      'Authorization': this.authService.getToken()
    });
    let url = ''
    if(this.data.tip == 1){
      url = "http://localhost:8086/notification/post/" + this.data.id 
    }else{
      url = "http://localhost:8086/notification/comment/" + this.data.id 
    }

    this.http.delete<any>(url,  { headers: headers })
    .subscribe(
      data => {
        this.popupService.showPopup('Notificarea a fost ștearsă cu succes!');
        console.log("Notificare stearsa")
        window.location.reload();

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
        if(error.status == 404){
         //this.popupService.showPopup('Notificarea a fost ștearsă cu succes!');
         // this.router.navigate(["/notifications"]);
          window.location.reload();

        }
        if(error.status == 200){
          //this.popupService.showPopup('Notificarea a fost ștearsă cu succes!');
          // this.router.navigate(["/notifications"]);
           window.location.reload();
 
         }
      }
    );
  }
}
