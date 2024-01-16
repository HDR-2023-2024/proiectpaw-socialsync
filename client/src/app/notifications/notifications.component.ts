
import { NotificationsServiceService } from '../notifications-service.service';
import { AuthService } from '../auth.service';
import { Component, Input, Inject } from '@angular/core';
import { Router } from '@angular/router';
import { DataHomeService } from '../data-home.service';
import { ScroolServiceService } from '../scrool-service.service';
import { debounceTime, elementAt, filter } from 'rxjs/operators';
import { ActivatedRoute } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LOCAL_STORAGE, StorageService } from 'ngx-webstorage-service';

interface Notification {
  tip: number,
  id: string;
  creatorId: string;
  topicId: string;
  messageType: string | null;
  postId: string | null;
  title: string;
  topicTitle: string;
  postTitle: string;
  comm:string
}

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent {
  notifications: { title: string; content: string; date: string }[] = [
    { title: "Răspuns la postarea ta", content: "Utilizatorul X a răspuns la postarea ta în thread-ul Y.", date: "2024-01-01" },
    { title: "Mesaj nou în thread-ul urmărit", content: "Thread-ul Z la care ești abonat/a are un mesaj nou.", date: "2024-01-02" },
    { title: "Postare marcată ca soluție", content: "Postarea ta în thread-ul W a fost marcată ca soluție.", date: "2024-01-03" },
    { title: "Feedback primit la postare", content: "Utilizatorul A a dat feedback la postarea ta în thread-ul B.", date: "2024-01-04" },
    { title: "Anunț important", content: "Administrare: Anunț important privind schimbări în regulile forumului.", date: "2024-01-05" },
    { title: "Întâlnire locală", content: "Comunitatea organizează o întâlnire locală pe data de 10 februarie.", date: "2024-01-06" }];
  myArr1: Array<Notification> | null = null
  myArr2: Array<Notification> | null = null
  myArr: any[] = [];

  notificationCount: number = 0;
  constructor(private notification: NotificationsServiceService, @Inject(LOCAL_STORAGE) private storage: StorageService, public authService: AuthService, private router: Router, private dataService: DataHomeService, private scrollService: ScroolServiceService, private route: ActivatedRoute) { }

  loadDataOnPageLoad(): void {
    let url;
    let userId: any = this.storage.get("userToShow");
    url = 'http://localhost:8086/api/v1/query/users/' + userId + '/topics?page=' + 0;
    this.dataService.getData(url).subscribe(
      (data) => {
       //console.log('Datele de la server:', data);
        this.myArr = data;
        var seenIds: Record<string, boolean> = {};
        var filteredArr = this.myArr.filter(function (item: any) {
          if (seenIds.hasOwnProperty(item.id)) {
            return false;
          }
          seenIds[item.id] = true;
          return true;
        });
        this.myArr = filteredArr;
        this.notification.getData("http://localhost:8086/notification/post/" + this.authService.getId()).subscribe(
          (data: Notification[]) => {
           // console.log('Datele de la server:', data);
            this.myArr1 = []
            data.forEach(element => {
              if (element.postId != null) {
                this.myArr1 = data.filter(element => element.postId != null);
              }
            });
       
            this.myArr1.forEach(element => {
              element.tip = 1;
            });
            let myCppy : Array<Notification> = []
            this.myArr1.forEach(element1 => {
              const matchingTopic = this.myArr.find(topic => topic.id === element1.topicId);
              if (matchingTopic) {
                element1.topicTitle = matchingTopic.name;
                console.log(element1.topicTitle)
                myCppy.push(element1);
              }
            });
            this.myArr1 = myCppy;
            console.log("Agregat: ");
            console.log(this.myArr1);
            this.notificationCount += this.myArr1.length
          },
          (error) => {
            console.error('Eroare la incarcarea datelor:', error);
          }
        );
      },
      (error) => {
        this.router.navigate(['/internal-server-error']);
        console.error('Eroare la incarcarea datelor:', error);
      }
    );
  }

  ngOnInit() {
    let url;
    this.loadDataOnPageLoad();
   

    this.notification.getData("http://localhost:8086/notification/comment/" + this.authService.getId()).subscribe(
      (data: Notification[]) => {
     //   console.log('Datele de la server:', data);
        this.myArr2 = []
        data.forEach(element => {
          if (element.postId != null) {
            this.myArr2 = data;
          }
        });
        console.log(this.myArr2)
        this.myArr2.forEach(element => {
          element.tip = 2;
        });
        this.notificationCount += this.myArr2.length
      },
      (error) => {
        console.error('Eroare la incarcarea datelor:', error);
      }
    );

    

  }


}

