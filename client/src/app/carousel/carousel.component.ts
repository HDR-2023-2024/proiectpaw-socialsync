import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { DataHomeService } from '../data-home.service';
import { ScroolServiceService } from '../scrool-service.service';
import { debounceTime, filter } from 'rxjs/operators';
import { ActivatedRoute } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-carousel',
  templateUrl: './carousel.component.html',
  styleUrls: ['./carousel.component.css']
})
export class CarouselComponent {
  start = 1;
  numElementsToShow = 3;
  constructor(public authService: AuthService, private router: Router, private dataService: DataHomeService, private scrollService: ScroolServiceService, private route: ActivatedRoute) {}
  
  myArr: any[] = [

  ];

  next() {
    console.log(this.start)
    if (this.start < this.myArr.length - 2) {
      this.start++;
    }
  }

  prev() {
    console.log(this.start)
    if (this.start > 1) {
      this.start--;
    }
  }

  loadDataOnPageLoad(): void {
    let url;
      url = 'http://localhost:8086/api/v1/query/posts?page=0';

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
      },
      (error) => {
        console.error('Eroare la incarcarea datelor:', error);
        this.router.navigate(['/internal-server-error']);
      }
    );
  }

  transformCharToNumber(char: string): number {

    if (char >= 'a' && char <= 'z') {

      return char.charCodeAt(0) - 'a'.charCodeAt(0);
    }
  

    if (char >= '0' && char <= '9') {
      return parseInt(char, 10);
    }
  
    return -1;
  }

  navigateToPost(id: string) {
    console.log("ceva")
    this.router.navigate(['/full-post', id]);
  }

  ngOnInit(){
    this.loadDataOnPageLoad();
  }
}

