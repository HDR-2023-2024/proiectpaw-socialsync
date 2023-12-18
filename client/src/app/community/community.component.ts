import { Component } from '@angular/core';
import { CommunityService } from '../community.service';
import { Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
@Component({
  selector: 'app-community',
  templateUrl: './community.component.html',
  styleUrls: ['./community.component.css']
})

export class CommunityComponent {
  joined: boolean = false;
  isNotified : boolean = false;
  @Input() data:any;
  posts: any[]=[];
  page= 0;
  postId:string='';
  selectedSortOption: string = ' ';

  constructor(private route:ActivatedRoute, private communityService:CommunityService){}

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.postId = params['id'];
      this.loadData();
      this.loadPosts();
    });
  }

  toggleJoinState() {
    this.joined = !this.joined;
  }
  
  toggleNotifyState(){
    this.isNotified = !this.isNotified; 
  }

  loadData(): void {
    this.communityService.getCommunityById(this.postId, this.page.toString()).subscribe(
      (response) => {
        console.log('Datele de la server:', response);
        this.data = response; 
      },
      (error) => {
        console.error('Eroare la încărcarea datelor.', error);
      }
    );
  }

  loadPosts(): void {
    this.communityService.getCommunityPostsById(this.postId, this.page.toString()).subscribe(
      (response) => {
        console.log(this.postId)
        console.log('Datele de la server:', response);
        this.posts = response; 
      },
      (error) => {
        console.error('Eroare la încărcarea datelor.', error);
      }
    );
  }
}


