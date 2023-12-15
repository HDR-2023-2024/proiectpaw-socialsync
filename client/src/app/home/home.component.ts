import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { DataHomeService } from '../data-home.service';
import { ScroolServiceService } from '../scrool-service.service';
import { debounceTime, filter } from 'rxjs/operators';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})

export class HomeComponent {
  private page = 0;

  constructor(public authService: AuthService, private router: Router,private dataService: DataHomeService,private scrollService: ScroolServiceService) { }

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
    this.router.navigate(['/post']);
  }

  private handleScrollEnd(): void {
    this.page++;
  
    this.dataService.getDataSync(this.page.toString())
      .then((data: any[]) => {
        if (data !== undefined) {
          for (const item of data) {
            this.myArr.push(item);
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
    this.dataService.getData(this.page.toString()).subscribe(
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