import { Component } from '@angular/core';

@Component({
  selector: 'app-community-details',
  templateUrl: './community-details.component.html',
  styleUrls: ['./community-details.component.css']
})
export class CommunityDetailsComponent {
  community: any[] = [
    {
      nume: "Comunitatea Superioară",
      img: "assets/images/avatar.png",
      membersCount: 1000,  
      creationDate: "1 ianuarie 2020", 
      description: "Aceasta este comunitatea noastră superbă în care discutăm despre tot ce ne pasionează. Alăturați-vă nouă pentru a împărtăși experiențe și cunoștințe!",  // Descriere
      moderators: ["Moderator 1", "Moderator 2"] 
    }
  ];
  

}
