import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { DataHomeService } from '../data-home.service';
import { ScroolServiceService } from '../scrool-service.service';
import { debounceTime, filter } from 'rxjs/operators';
import { ActivatedRoute } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-carousel-users',
  templateUrl: './carousel-users.component.html',
  styleUrls: ['./carousel-users.component.css']
})
export class CarouselUsersComponent {
  constructor(public authService: AuthService, private router: Router, private dataService: DataHomeService, private scrollService: ScroolServiceService, private route: ActivatedRoute) { }

  start = 1;
  numElementsToShow = 3;
  query = '';

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

  ngOnInit() {
    let url;

    if (this.query == null || this.query.length == 0) {
      url = 'http://localhost:8086/api/v1/query/users?page=' + "0";
    }
    else {
      url = 'http://localhost:8086/api/v1/query/users?page=' + "0" + "&query=" + this.query;
    }
    this.dataService.getDataTopics(url).subscribe(
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


  navigateToUser(id: string) {
    console.log("ceva")
    this.router.navigate(['/user', id]);
  }

}

