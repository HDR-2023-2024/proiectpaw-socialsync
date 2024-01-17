import { Component,Input,Inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { DataHomeService } from '../data-home.service';
import { ScroolServiceService } from '../scrool-service.service';
import { debounceTime, filter } from 'rxjs/operators';
import { ActivatedRoute } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LOCAL_STORAGE, StorageService } from 'ngx-webstorage-service';

@Component({
  selector: 'app-profile-link-community',
  templateUrl: './profile-link-community.component.html',
  styleUrls: ['./profile-link-community.component.css']
})
export class ProfileLinkCommunityComponent {
  constructor(@Inject(LOCAL_STORAGE) public storage: StorageService,public authService: AuthService, private router: Router, private dataService: DataHomeService, private scrollService: ScroolServiceService, private route: ActivatedRoute) {
    this.route.params.subscribe(params => {
      this.page = 0;
      this.loadDataOnPageLoad();
    });
  }

  myArr: any[] = [];

  private page = 0;

  @Input() userId: any = null;

  ngOnInit(): void {
    this.loadDataOnPageLoad();
    this.scrollService.getScrollObservable()
      .pipe(
        debounceTime(200),
        filter(() => this.scrollService.isScrolledToBottom())
      )
      .subscribe(() => {
        this.handleScrollEnd();
      });
  }


  redirectToPostPage(): void {
    this.router.navigate(['/create-post']);
  }

  private handleScrollEnd(): void {
    if (this.myArr.length > 10) {
      this.page++;
    }
    let url = "";
     this.userId  =  this.storage.get("userToShow");
    url = 'http://localhost:8086/api/v1/query/users/' + this.userId + '/topics?page=' + this.page.toString();
    let oldSize = this.myArr.length;
    let headers = new HttpHeaders({
      'Authorization': this.authService.getToken()
    });
    this.dataService.getDataSync(url)
      .then((data: any[]) => {
        if (data !== undefined) {
          for (const item of data) {
            this.myArr.push(item);
          }

          var seenIds: Record<string, boolean> = {};
          var filteredArr = this.myArr.filter(function (item: any) {
            if (seenIds.hasOwnProperty(item.id)) {
              return false;
            }
            seenIds[item.id] = true;
            return true;
          });
          this.myArr = filteredArr;
          console.log(oldSize)
          console.log(this.myArr.length)
          if (this.myArr.length == oldSize) {
            this.page--;
          }
        } else {
          console.log("Get goll");
        }
      })
      .catch(error => {
        this.router.navigate(['/internal-server-error']);
        console.error('Eroare:', error);
      });
  }

  loadDataOnPageLoad(): void {
    let url;
    this.userId =  this.storage.get("userToShow");
    url = 'http://localhost:8086/api/v1/query/users/' + this.userId + '/topics?page=' + this.page.toString();
    this.dataService.getData(url).subscribe(
      (data) => {
        console.log('Datele de la server:', data);
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
      },
      (error) => {
        this.router.navigate(['/internal-server-error']);
        console.error('Eroare la incarcarea datelor:', error);
      }
    );
  }

  redirectToCreateTopicPage(): void {
    this.router.navigate(['/create-topics']);
  }
}