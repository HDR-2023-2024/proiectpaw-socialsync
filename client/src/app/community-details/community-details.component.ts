import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommunityService } from '../community.service';
import { Input } from '@angular/core';

@Component({
  selector: 'app-community-details',
  templateUrl: './community-details.component.html',
  styleUrls: ['./community-details.component.css']
})
export class CommunityDetailsComponent {
  joined: boolean = false;
  isNotified : boolean = false;
  @Input() data:any;
  page= 0;
  communityId:string='';
  formattedDate?: string;

  constructor(private route:ActivatedRoute, private communityService:CommunityService){}

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
 
  formatTimestampToDate(timestamp: number):string {
     const date = new Date(timestamp);

    const day = ('0' + date.getDate()).slice(-2);
    const month = ('0' + (date.getMonth() + 1)).slice(-2);
    const year = date.getFullYear();

    this.formattedDate = `${day}-${month}-${year}`;
    return this.formattedDate;
  }

}
