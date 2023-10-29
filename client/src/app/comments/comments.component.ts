import { Component } from '@angular/core';

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})
export class CommentsComponent {

  comments: any[] = [
    {
      username: 'Utilizator1',
      time: '2 ore în urmă',
      content: 'Excelent articol!',
      img: "assets/images/avatar.png",
      points: 10
    },
    {
      username: 'Utilizator2',
      time: '3 ore în urmă',
      content: 'Frumos articol!',
      img: "assets/images/avatar.png",
      points: 5
    },
  ];

}
