import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommunityService } from '../community.service';
import { Input } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-community-details',
  templateUrl: './community-details.component.html',
  styleUrls: ['./community-details.component.css']
})
export class CommunityDetailsComponent {
  joined: boolean = false;
  isNotified : boolean = false;
  @Input() data:any | null = null;
  page= 0;
  communityId:string='';
  formattedDate?: string;

  constructor(private route:ActivatedRoute, private communityService:CommunityService,private router: Router){}

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.communityId = params['id'];
      this.loadData();
      console.log(this.communityId);
    });
  }

  loadData(): void {
    this.communityService.getCommunityById(this.communityId, this.page.toString()).subscribe(
      (response) => {
        console.log('Datele de la server:', response);
        this.data = response; 
      },
      (error) => {
        console.error('Eroare la încărcarea datelor.', error);
      }
    );
  }
 
  convertTimestampToDateTime(timestamp: number) {
    var date = new Date(timestamp * 1000);
    return date.toLocaleString();
  }


  navigateToPost(topicId: string,topicName : string) {
    this.router.navigate(['/create-post', topicId, topicName]);
  }
}
