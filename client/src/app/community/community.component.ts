import { Component } from '@angular/core';
import { CommunityService } from '../community.service';
import { Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { CreateTopicService } from '../create-topic.service';
import { PopupServiceService } from '../popup-service.service';

@Component({
  selector: 'app-community',
  templateUrl: './community.component.html',
  styleUrls: ['./community.component.css']
})

export class CommunityComponent {
  joined: boolean = false;
  isNotified : boolean = false;
  data:any;
  posts: any[]=[];
  page= 0;
  postId:string='';
  selectedSortOption: string = ' ';

  constructor(private route:ActivatedRoute, private communityService:CommunityService, public authService: AuthService,private createTopic: CreateTopicService,private router:Router, private popupService: PopupServiceService){}

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
        this.data = response; 
        console.log(this.data);
        if(this.data.photoId.length == 0){
          // pentru afisare poza implicita
          this.data.photoId = null;
        }
      },
      (error) => {
        console.log(error);
        this.router.navigate(['/internal-server-error']);
      }
    );
  }

  loadPosts(): void {
    this.communityService.getCommunityPostsById(this.postId, this.page.toString()).subscribe(
      (response) => {
        this.posts = response; 
      },
      (error) => {
        console.log(error);
        this.router.navigate(['/internal-server-error']);
      }
    );
  }

  deleteTopic(){
    this.createTopic.delete(this.data.id);
    this.popupService.showPopup('Comunitate ștearsă cu succes.');
  }

  editTopic(){
    this.router.navigate(['edit-topic/', this.data.id]);
  }
}


