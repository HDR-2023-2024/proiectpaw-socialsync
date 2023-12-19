import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { DataHomeService } from '../data-home.service';
import { ScroolServiceService } from '../scrool-service.service';
import { debounceTime, filter } from 'rxjs/operators';
import { ActivatedRoute } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})

export class HomeComponent {
  private page = 0;
  private query: string | null = null;

  constructor(public authService: AuthService, private router: Router, private dataService: DataHomeService, private scrollService: ScroolServiceService, private route: ActivatedRoute) {
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


  redirectToPostPage(): void {
    this.router.navigate(['/create-post']);
  }

  private handleScrollEnd(): void {
    this.page++;
    let url = "";
    if (this.query == null || this.query.length == 0) {
      url = 'http://localhost:8086/api/v1/query/posts?page=' + this.page.toString();
    }
    else {
      url = 'http://localhost:8086/api/v1/query/posts?page=' + this.page.toString() + "&query=" + this.query;
    }
    let oldSize = this.myArr.length;
    this.dataService.getDataSync(url)
      .then((data: any[]) => {
        if (data !== undefined) {
          for (const item of data) {
            this.myArr.push(item);
          }
          if (this.myArr.length == oldSize) {
            this.page--;
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
      url = 'http://localhost:8086/api/v1/query/posts?page=' + this.page.toString();
    }
    else {
      url = 'http://localhost:8086/api/v1/query/posts?page=' + this.page.toString() + "&query=" + this.query;
    }

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
        console.error('Eroare la incarcarea datelor:', error);
      }
    );
  }
}