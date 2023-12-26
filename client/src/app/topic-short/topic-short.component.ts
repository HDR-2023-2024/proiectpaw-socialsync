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

}
