import { Component, Input } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';


@Component({
  selector: 'app-topic-short',
  templateUrl: './topic-short.component.html',
  styleUrls: ['./topic-short.component.css']
})
export class TopicShortComponent {
  @Input() data: any
  constructor(public authService: AuthService, private router: Router, private http: HttpClient) { }

  navigateToCommunity(id: string) {
    this.router.navigate(['community/', id]);
  }

  navigateToCategory(){
    console.log(`Item clicked: ${this.data.categorie}`);
    let modificat = this.data.categorie.replace("ă","a").replace("ț","t").replace("â","a").replace("ș","s").replace("Î","I").replace(" ","_").replace("Ț","T").replace("-","_");
    this.router.navigate(['/view-topics', { category: modificat }]);
  }

  convertTimestampToDateTime(timestamp: number) {
    var date = new Date(timestamp * 1000);
    return date.toLocaleString();
  }

}
