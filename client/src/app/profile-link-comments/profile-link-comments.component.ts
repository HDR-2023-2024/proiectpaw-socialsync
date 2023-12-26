import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile-link-comments',
  templateUrl: './profile-link-comments.component.html',
  styleUrls: ['./profile-link-comments.component.css']
})
export class ProfileLinkCommentsComponent {
  constructor(private router: Router) { }
  Comments: any[] = [
    {
      id: 1,
      comunity: "SomeCult",
      author: 'AnaAreMere11',
      commenter: 'Commenter123',
      time: "2 ore în urmă",
      img: "assets/images/avatar.png",
      title: "Noul smartphone revoluționar: Ce aduce în plus?",
      content: "I feel offended!",
      points: 10,
      saved: false
    },
    {
      id: 2,
      author: 'User456',
      comunity: "SomeCult",
      commenter: 'AnotherCommenter',
      time: "1 zi în urmă",
      img: "assets/images/avatar.png",
      title: "Explorarea Muntelui Everest: O aventură epică",
      content: "Great article!",
      points: 15,
      saved: true
    },
    {
      id: 3,
      author: 'TechEnthusiast',
      comunity: "SomeCult",
      commenter: 'TechFanatic',
      time: "5 ore în urmă",
      img: "assets/images/avatar.png",
      title: "Tendințe în tehnologie pentru anul 2023",
      content: "Incredible tech insights!",
      points: 20,
      saved: false
    },
    {
      id: 4,
      author: 'Fashionista101',
      comunity: "SomeCult",
      commenter: 'StyleGuru',
      time: "3 zile în urmă",
      img: "assets/images/avatar.png",
      title: "Tendințe de modă pentru sezonul primăvară-vară 2023",
      content: "Love the fashion tips!",
      points: 25,
      saved: true
    }
  ];


  navigateToPost(postId: number) {
    this.router.navigate(['/full-post', postId]);
  }
}
