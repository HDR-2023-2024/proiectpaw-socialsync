import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommunityService } from '../community.service';
import { Input } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';


interface Member {
  id: null;
  photoId: null;
  username: null;
  description: null;
  role: null;
}

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
  isMember = false;


  constructor(private route:ActivatedRoute, private communityService:CommunityService,private router: Router, public authService: AuthService){}

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.communityId = params['id'];
      this.loadData();
      //console.log(this.communityId);
    });
  }

  loadData(): void {
    this.communityService.getCommunityById(this.communityId, this.page.toString()).subscribe(
      (response) => {
       // console.log('Datele de la server:', response);
        this.data = response; 
        this.data.members.forEach((element: Member) => {
          if (element.id == this.authService.getId()) {
            this.isMember = true;
            
          }
        });
        console.log(this.isMember);
      },
      (error) => {
        this.router.navigate(['/internal-server-error']);
        console.error('Eroare la incarcarea datelor.', error);
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

  navigateToUser(id : string){
    console.log("Ceva2")
    this.router.navigate(['/user', id]);
  }

  navigateToCategory(){
    console.log(`Item clicked: ${this.data.categorie}`);
    let modificat = this.data.categorie.replace("ă","a").replace("ț","t").replace("â","a").replace("ș","s").replace("Î","I").replace(" ","_").replace("Ț","T").replace("-","_");
    this.router.navigate(['/view-topics', { category: modificat }]);
  }
}
