import { Component } from '@angular/core';
import { CommunityService } from '../community.service';
import { Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { CreateTopicService } from '../create-topic.service';
import { PopupServiceService } from '../popup-service.service';
import { JoinToTopicService } from '../join-to-topic.service';
import { Location } from '@angular/common';

interface Member {
  id: null;
  photoId: null;
  username: null;
  description: null;
  role: null;
}

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
  isMember = false;

  constructor(private route:ActivatedRoute, private communityService:CommunityService, public authService: AuthService,private createTopic: CreateTopicService,private router:Router, private popupService: PopupServiceService,private joinToTopic : JoinToTopicService,private location: Location){}

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.postId = params['id'];
      this.loadData();
      this.loadPosts();
    });
  }

  async toggleJoinState() {
    this.joined = !this.joined;
    try {
      await this.joinToTopic.join(this.data.id);
      console.log("ceva");
      this.location.go(this.location.path());
      window.location.reload();
  
    } catch (error) {

      console.error('Eroare la apelul de join:', error);
    }
 
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
        this.data.members.forEach((element: Member) => {
          if (element.id == this.authService.getId()) {
            this.isMember = true;
            
          }
        });
        console.log(this.isMember);

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


