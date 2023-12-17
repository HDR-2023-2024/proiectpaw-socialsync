import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { DataHomeService } from '../data-home.service';
import { ScroolServiceService } from '../scrool-service.service';
import { debounceTime, filter } from 'rxjs/operators';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-view-topics',
  templateUrl: './view-topics.component.html',
  styleUrls: ['./view-topics.component.css']
})
export class ViewTopicsComponent {
  private page = 0;
  private query : string | null = null;


  constructor(public authService: AuthService, private router: Router, private dataService: DataHomeService, private scrollService: ScroolServiceService,private route: ActivatedRoute) {
    this.route.params.subscribe(params => {
      this.query = params['query'];
      this.page = 0;
      console.log('Valoarea primita:', this.query);
      this.loadDataOnPageLoad();
    });
   }

  myArr: any[] = []

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


  redirectToCreateTopicPage(): void {
    //this.router.navigate(['/post']);
  }

  private handleScrollEnd(): void {
    this.page++;
    let url  = "";
    if (this.query == null || this.query.length == 0) {
      url = 'http://localhost:8086/api/v1/query/topics?page=' + this.page.toString() ;
    }
    else {
      url = 'http://localhost:8086/api/v1/query/topics?page=' + this.page.toString() + "&query=" + this.query;
    }
    let oldSize = this.myArr.length;
    this.dataService.getDataSyncTopics(url)
      .then((data: any[]) => {
      if (data !== undefined) {
        for (const item of data) {
          this.myArr.push(item);
        }
        if(this.myArr.length == oldSize){
          this.page--;
        }

        console.log(this.myArr.length);
      } else {
        console.log("Get goll");
      }
    })
      .catch(error => {
        console.error('Eroare:', error);
      });
  }

  loadDataOnPageLoad(): void {
    let url;
    if (this.query == null || this.query.length == 0) {
      url = 'http://localhost:8086/api/v1/query/topics?page=' + this.page.toString() ;
    }
    else {
      url = 'http://localhost:8086/api/v1/query/topics?page=' + this.page.toString() + "&query=" + this.query;
    }
    this.dataService.getDataTopics(url).subscribe(
      (data) => {
        console.log('Datele de la server:', data);
        this.myArr = data;
      },
      (error) => {
        console.error('Eroare la incarcarea datelor:', error);
      }
    );
  }
}